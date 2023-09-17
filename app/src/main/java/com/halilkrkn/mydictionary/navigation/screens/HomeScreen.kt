package com.halilkrkn.mydictionary.navigation.screens

sealed class HomeScreen(val route: String) {
    object Home: HomeScreen("home")
}