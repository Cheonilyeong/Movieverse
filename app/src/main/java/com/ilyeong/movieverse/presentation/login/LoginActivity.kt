package com.ilyeong.movieverse.presentation.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import com.ilyeong.movieverse.databinding.ActivityLoginBinding
import com.ilyeong.movieverse.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setDarkStatusBarTheme()
        handleDeepLink(intent)
        setUpBtnLogin()
        observeEvents()
    }

    private fun setDarkStatusBarTheme() {
        val insetsController = WindowInsetsControllerCompat(window, window.decorView)
        insetsController.isAppearanceLightStatusBars = false
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
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
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
                        }

                        is LoginEvent.ShowMessage -> {
                            Snackbar.make(
                                binding.root,
                                event.error.message.toString(),
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }
}
