package com.halilkrkn.mydictionary.presantation.auth

data class SignInState(
    val isSingInSuccessful: Boolean = false,
    val signInError: String? = null
)

