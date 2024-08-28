package com.example.cryptoapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cryptoapp.ui.screens.AllCoinsScreen
import com.example.cryptoapp.ui.screens.HistoryScreen
import com.example.cryptoapp.ui.screens.MainScreen
import com.example.cryptoapp.ui.screens.SplashScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.SPLASH.name) {
        composable(Screen.SPLASH.name) { SplashScreen(navController) }
        composable(Screen.HOME.name) { MainScreen(navController) }
        composable(Screen.AllCoins.name) { AllCoinsScreen(navController) }
        composable(Screen.History.name) { HistoryScreen(navController) }
    }
}


enum class Screen {
    SPLASH,
    HOME,
    AllCoins,
    History,
}

sealed class NavigationItem(val route: String) {
    data object Splash : NavigationItem(Screen.SPLASH.name)
    data object Home : NavigationItem(Screen.HOME.name)
    data object AllCoins : NavigationItem(Screen.AllCoins.name)
    data object History : NavigationItem(Screen.AllCoins.name)
}