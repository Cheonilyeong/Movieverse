package com.ilyeong.movieverse.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import coil3.load
import coil3.request.crossfade
import coil3.request.error
import com.ilyeong.movieverse.R
import com.ilyeong.movieverse.databinding.FragmentProfileBinding
import com.ilyeong.movieverse.presentation.common.fragment.BaseFragment
import com.ilyeong.movieverse.presentation.login.LoginActivity
import com.ilyeong.movieverse.presentation.profile.model.ProfileEvent.NavigateToLogin
import com.ilyeong.movieverse.presentation.profile.model.ProfileEvent.ShowMessage
import com.ilyeong.movieverse.presentation.profile.model.ProfileUiState.Failure
import com.ilyeong.movieverse.presentation.profile.model.ProfileUiState.Loading
import com.ilyeong.movieverse.presentation.profile.model.ProfileUiState.Success
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override val viewBindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfileBinding
        get() = FragmentProfileBinding::inflate

    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLogoutBtn()

        observeUiState()
        observeEvents()

        loadData()
    }

    private fun setLogoutBtn() {
        binding.tvLogout.setOnClickListener {
            viewModel.logout()  // todo: 다이얼로그
        }
    }

    private fun observeUiState() {
        repeatOnViewStarted {
            viewModel.uiState.collect {
                when (it) {
                    is Loading -> {
                        binding.ivAvatar.setImageResource(R.drawable.ic_profile_filled_gray_24)
                        binding.tvUserName.text = null
                    }

                    is Success -> {
                        binding.ivAvatar.load(it.account.avatarPath) {
                            crossfade(true)
                            error(R.drawable.ic_profile_filled_gray_24)
                        }
                        binding.tvUserName.text = it.account.username
                    }

                    is Failure -> {
                        binding.ivAvatar.setImageResource(R.drawable.ic_profile_filled_gray_24)
                        binding.tvUserName.text = getString(R.string.unknown_user)
                    }
                }
            }
        }
    }

    private fun observeEvents() {
        repeatOnViewStarted {
            viewModel.events.collect {
                when (it) {
                    is NavigateToLogin -> {
                        val intent = Intent(requireContext(), LoginActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }

                    is ShowMessage -> {
                        showMessage(getString(it.message))
                    }
                }
            }
        }
    }

    private fun loadData() {
        viewModel.loadData()
    }
}