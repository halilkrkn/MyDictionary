package com.halilkrkn.mydictionary.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.halilkrkn.mydictionary.navigation.screens.BottomBarScreen
import com.halilkrkn.mydictionary.navigation.screens.HomeScreen
import com.halilkrkn.mydictionary.navigation.util.Graphs
import com.halilkrkn.mydictionary.presantation.main.MainScreen

fun NavGraphBuilder.homeNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = HomeScreen.Home.route,
        route = Graphs.HOME
    ) {
        composable(
            route = HomeScreen.Home.route
        ) {
            MainScreen()
        }
    }
}