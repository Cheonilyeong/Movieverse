package com.ilyeong.movieverse.presentation.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ilyeong.movieverse.data.repository.AuthRepository
import com.ilyeong.movieverse.presentation.login.LoginEvent.NavigateToCustomTabs
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
            val requestToken = authRepository.createRequestToken()
            _events.emit(
                NavigateToCustomTabs(
                    "https://www.themoviedb.org/authenticate/${requestToken.requestToken}?redirect_to=ilyeong://movieverse"
                )
            )
        }
    }

    fun createSessionId(requestToken: String) {
        viewModelScope.launch {
            authRepository.createSessionId(requestToken)
        }
    }
}

sealed interface LoginEvent {
    data class NavigateToCustomTabs(val url: String) : LoginEvent
}