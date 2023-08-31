package com.halilkrkn.mydictionary.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.halilkrkn.mydictionary.presantation.homeScreen.HomeScreen
import com.halilkrkn.mydictionary.presantation.signInScreen.SignInScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
) {

    NavHost(
        navController = navController,
        startDestination = Screens.SignIn.route
    ) {
        composable(Screens.SignIn.route) {
            SignInScreen(navController = navController)
        }

        composable(Screens.Home.route) {
            HomeScreen(navController = navController)
        }
    }
}