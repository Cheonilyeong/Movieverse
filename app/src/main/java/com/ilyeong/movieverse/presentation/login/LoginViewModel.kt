package com.ilyeong.movieverse.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilyeong.movieverse.data.repository.AuthRepository
import com.ilyeong.movieverse.presentation.login.model.LoginEvent
import com.ilyeong.movieverse.presentation.login.model.LoginEvent.NavigateToCustomTabs
import com.ilyeong.movieverse.presentation.login.model.LoginEvent.NavigateToMain
import com.ilyeong.movieverse.presentation.login.model.LoginEvent.ShowMessage
import com.ilyeong.movieverse.presentation.login.model.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<LoginEvent>()
    val events = _events.asSharedFlow()

    fun createRequestToken() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }

                val requestToken = authRepository.createRequestToken()
                _events.emit(NavigateToCustomTabs("https://www.themoviedb.org/authenticate/${requestToken.requestToken}?redirect_to=ilyeong://movieverse"))

                _uiState.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                _events.emit(ShowMessage(e))
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun createSessionId(requestToken: String) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }

                authRepository.createSessionId(requestToken)
                _events.emit(NavigateToMain)

                _uiState.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                _events.emit(ShowMessage(e))
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}