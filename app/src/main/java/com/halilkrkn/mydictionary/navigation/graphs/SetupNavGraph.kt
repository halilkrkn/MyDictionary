package com.halilkrkn.mydictionary.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.halilkrkn.mydictionary.navigation.screens.AuthScreen
import com.halilkrkn.mydictionary.navigation.util.Graphs
import com.halilkrkn.mydictionary.presantation.main.home.HomeScreen
import com.halilkrkn.mydictionary.presantation.auth.SignInScreen
import com.halilkrkn.mydictionary.presantation.main.MainScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        route = Graphs.ROOT,
        startDestination = Graphs.AUTHENTICATION
    ) {
        authNavGraph(navController = navController)
        homeNavGraph(navController = navController)
//        composable(route = Graphs.HOME) {
//            MainScreen()
//        }
    }
}