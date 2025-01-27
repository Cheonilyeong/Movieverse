package com.ilyeong.movieverse.presentation.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ilyeong.movieverse.data.repository.AuthRepository
import com.ilyeong.movieverse.presentation.login.LoginEvent.NavigateToCustomTabs
import com.ilyeong.movieverse.presentation.login.LoginEvent.NavigateToMain
import com.ilyeong.movieverse.presentation.login.LoginEvent.ShowMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val login: Login = savedStateHandle.toRoute<Login>()

    init {
        if (login.requestToken != null && login.approved == true) {
            createSessionId(login.requestToken)
        }
    }

    private val _events = MutableSharedFlow<LoginEvent>()
    val events = _events.asSharedFlow()

    fun createRequestToken() {
        viewModelScope.launch {
            try {
                val requestToken = authRepository.createRequestToken()
                _events.emit(
                    NavigateToCustomTabs(
                        "https://www.themoviedb.org/authenticate/${requestToken.requestToken}?redirect_to=ilyeong://movieverse"
                    )
                )
            } catch (e: Exception) {
                _events.emit(ShowMessage(e))
            }
        }
    }

    fun createSessionId(requestToken: String) {
        viewModelScope.launch {
            try {
                authRepository.createSessionId(requestToken)
                _events.emit(NavigateToMain)
            } catch (e: Exception) {
                _events.emit(ShowMessage(e))
            }
        }
    }
}

sealed interface LoginEvent {
    data class NavigateToCustomTabs(val url: String) : LoginEvent
    data object NavigateToMain : LoginEvent
    data class ShowMessage(val error: Throwable) : LoginEvent
}