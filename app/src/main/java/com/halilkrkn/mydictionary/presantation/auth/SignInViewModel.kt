package com.halilkrkn.mydictionary.presantation.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import com.halilkrkn.mydictionary.data.auth.SignInResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(): ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: SignInResult) {
        _state.update { signInState ->
            signInState.copy(
                isSingInSuccessful = result.data != null,
                signInError = result.errorMessage
            )
        }
        Log.d("SignInViewModel", "onSignInResult: ${result.data?.username}")
        Log.d("SignInViewModel", "onSignInResult: ${result.data?.profilePictureUrl}")
        Log.d("SignInViewModel", "onSignInResult: ${result.data?.email}")
        Log.d("SignInViewModel", "onSignInResult: ${result.data?.userId}")
    }

    fun resetSignInState() {
        _state.update { signInState ->
            signInState.copy(
                isSingInSuccessful = false,
                signInError = null
            )
        }
    }
}