package com.ilyeong.movieverse.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilyeong.movieverse.data.repository.AuthRepository
import com.ilyeong.movieverse.presentation.login.LoginEvent.NavigateToCustomTabs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _events = MutableSharedFlow<LoginEvent>()
    val events = _events.asSharedFlow()

    fun createRequestToken() {
        viewModelScope.launch {
            val token = authRepository.createRequestToken()
            Log.d("LoginViewModel", "createRequestToken: $token")
            _events.emit(
                NavigateToCustomTabs(
                    "https://www.themoviedb.org/authenticate/${token.requestToken}?redirect_to=ilyeong://movieverse"
                )
            )
        }
    }
}

sealed interface LoginEvent {
    data class NavigateToCustomTabs(val url: String) : LoginEvent
}