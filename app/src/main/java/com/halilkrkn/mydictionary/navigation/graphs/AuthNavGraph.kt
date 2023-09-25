package com.halilkrkn.mydictionary.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.halilkrkn.mydictionary.navigation.screens.AuthScreen
import com.halilkrkn.mydictionary.navigation.util.Graphs
import com.halilkrkn.mydictionary.presantation.auth.SignInScreen
import com.halilkrkn.mydictionary.presantation.splash.SplashScreen

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = AuthScreen.Splash.route,
        route = Graphs.AUTHENTICATION
    ) {
        composable(
            route = AuthScreen.Splash.route
        ) {
            SplashScreen(navController = navController)
        }
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