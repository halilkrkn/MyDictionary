package com.halilkrkn.mydictionary.navigation

sealed class Screens(val route: String){

    object SignIn: Screens("sign_in")
    object Home: Screens("home")

}
