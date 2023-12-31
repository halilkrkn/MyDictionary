package com.halilkrkn.mydictionary.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.halilkrkn.mydictionary.navigation.util.Graphs

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