package com.ilyeong.movieverse.presentation.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.browser.customtabs.CustomTabsIntent
import com.ilyeong.movieverse.databinding.ActivityLoginBinding
import com.ilyeong.movieverse.presentation.MainActivity
import com.ilyeong.movieverse.presentation.common.BaseActivity
import com.ilyeong.movieverse.presentation.login.model.LoginEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    override val viewBindingInflater: (inflater: LayoutInflater) -> ActivityLoginBinding
        get() = ActivityLoginBinding::inflate

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handleDeepLink(intent)
        setUpBtnLogin()
        observeEvents()
        observeUiState()
    }

    private fun handleDeepLink(intent: Intent?) {
        val action: String? = intent?.action
        val data: Uri? = intent?.data

        if (Intent.ACTION_VIEW == action && data != null) {
            val requestToken = data.getQueryParameter("request_token")
            val approved = data.getQueryParameter("approved")

            if (requestToken != null && approved == "true") {
                viewModel.createSessionId(requestToken)
            }
        }
    }

    private fun setUpBtnLogin() {
        binding.btnLogin.setOnClickListener {
            viewModel.createRequestToken()
        }
    }

    private fun observeEvents() {
        repeatOnStarted {
            viewModel.events.collect { event ->
                when (event) {
                    is LoginEvent.NavigateToCustomTabs -> {
                        val uri = Uri.parse(event.url)
                        CustomTabsIntent.Builder().build()
                            .launchUrl(this@LoginActivity, uri)
                    }

                    is LoginEvent.NavigateToMain -> {
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                    is LoginEvent.ShowMessage -> {
                        showMessage(event.error.message.toString())
                    }
                }
            }
        }
    }

    private fun observeUiState() {
        repeatOnStarted {
            viewModel.uiState.collect { uiState ->
                binding.pbLoading.isLoading = uiState.isLoading
            }
        }
    }
}
