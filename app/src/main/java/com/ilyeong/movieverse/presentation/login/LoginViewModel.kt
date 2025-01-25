package com.ilyeong.movieverse.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilyeong.movieverse.data.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun createRequestToken() {
        viewModelScope.launch {
            val result = authRepository.createRequestToken()
            Log.d("LoginViewModel", "createRequestToken: $result")
        }
    }
}