package com.halilkrkn.mydictionary.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.halilkrkn.mydictionary.navigation.screens.AuthScreen
import com.halilkrkn.mydictionary.navigation.screens.HomeScreen
import com.halilkrkn.mydictionary.navigation.util.Graphs
import com.halilkrkn.mydictionary.presantation.auth.SignInScreen
import com.halilkrkn.mydictionary.presantation.main.MainScreen

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = AuthScreen.SignIn.route,
        route = Graphs.AUTHENTICATION
    ) {
        composable(
            route = AuthScreen.SignIn.route
        ) {
            SignInScreen(navController = navController)
        }
        /*
                composable(
                    route = AuthScreen.SignUp.route
                ) {
                    SignUpScreen(navController = navController)
                }

                composable(
                    route = AuthScreen.Forgot.route
                ) {
                    ForgotScreen(navController = navController)
                }*/
    }
}