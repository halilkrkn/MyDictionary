package com.halilkrkn.mydictionary.navigation.screens

sealed class AuthScreen(val route: String){
    object SignIn: AuthScreen("sign_in")
}
