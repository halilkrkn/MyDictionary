package com.halilkrkn.mydictionary.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.halilkrkn.mydictionary.navigation.screens.BottomBarScreen
import com.halilkrkn.mydictionary.navigation.util.Graphs
import com.halilkrkn.mydictionary.presantation.main.favourite.FavouriteScreen
import com.halilkrkn.mydictionary.presantation.main.home.HomeScreen
import com.halilkrkn.mydictionary.presantation.main.search.SearchScreen
import com.halilkrkn.mydictionary.presantation.main.translation.TranslationScreen

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.HomeScreen.route,
        route = Graphs.HOME,
        modifier = modifier
    ) {

        composable(route = BottomBarScreen.HomeScreen.route){
            HomeScreen(navController = navController)
        }
        composable(route = BottomBarScreen.SearchScreen.route){
            SearchScreen(navController = navController)
        }

        composable(route = BottomBarScreen.TranslationScreen.route){
            TranslationScreen(navController = navController)
        }

        composable(route = BottomBarScreen.FavouriteScreen.route){
            FavouriteScreen(navController = navController)
        }

        authNavGraph(navController = navController)
    }
}