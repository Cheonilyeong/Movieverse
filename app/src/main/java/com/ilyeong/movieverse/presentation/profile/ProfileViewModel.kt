package com.ilyeong.movieverse.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilyeong.movieverse.R
import com.ilyeong.movieverse.data.repository.AuthRepository
import com.ilyeong.movieverse.data.repository.UserRepository
import com.ilyeong.movieverse.presentation.profile.model.ProfileEvent
import com.ilyeong.movieverse.presentation.profile.model.ProfileEvent.NavigateToLogin
import com.ilyeong.movieverse.presentation.profile.model.ProfileUiState
import com.ilyeong.movieverse.presentation.profile.model.ProfileUiState.Failure
import com.ilyeong.movieverse.presentation.profile.model.ProfileUiState.Loading
import com.ilyeong.movieverse.presentation.profile.model.ProfileUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<ProfileUiState> = MutableStateFlow(Loading)
    val uiState = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<ProfileEvent>()
    val events = _events.asSharedFlow()

    fun loadData() {
        if (_uiState.value is Success) return

        userRepository.getAccount()
            .onStart { _uiState.value = Loading }
            .onEach { _uiState.value = Success(it) }
            .catch { _uiState.value = Failure }
            .launchIn(viewModelScope)
    }

    fun logout() {
        authRepository.logout()
            .onEach { _events.emit(NavigateToLogin) }
            .catch { _events.emit(ProfileEvent.ShowMessage(R.string.fail_login_message)) }
            .launchIn(viewModelScope)
    }
}