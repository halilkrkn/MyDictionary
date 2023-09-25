package com.halilkrkn.mydictionary.navigation.screens

sealed class AuthScreen(val route: String){

    object Splash: AuthScreen("splash")
    object SignIn: AuthScreen("sign_in")
}
