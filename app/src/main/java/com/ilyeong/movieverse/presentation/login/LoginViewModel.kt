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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
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
        authRepository.createRequestToken()
            .onStart { _uiState.update { it.copy(isLoading = true) } }
            .onEach { _events.emit(NavigateToCustomTabs("https://www.themoviedb.org/authenticate/${it.requestToken}?redirect_to=ilyeong://movieverse")) }
            .onCompletion { _uiState.update { it.copy(isLoading = false) } }
            .catch { _events.emit(ShowMessage(it)) }
            .launchIn(viewModelScope)
    }

    fun createSessionId(requestToken: String) {
        authRepository.createSessionId(requestToken)
            .onStart { _uiState.update { it.copy(isLoading = true) } }
            .onEach { _events.emit(NavigateToMain) }
            .onCompletion { _uiState.update { it.copy(isLoading = false) } }
            .catch { _events.emit(ShowMessage(it)) }
            .launchIn(viewModelScope)
    }
}