package com.halilkrkn.mydictionary.navigation.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector,
) {

    object HomeScreen : BottomBarScreen(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )

    object SearchScreen : BottomBarScreen(
        route = "search",
        title = "Search",
        icon = Icons.Default.Search
    )

    object TranslationScreen : BottomBarScreen(
        route = "translation",
        title = "Translation",
        icon = Icons.Default.Settings
    )

    object FavouriteScreen : BottomBarScreen(
        route = "favourite",
        title = "Favourite",
        icon = Icons.Default.Favorite
    )

}