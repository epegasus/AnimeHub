package com.sohaib.animehub.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sohaib.animehub.feature.dashboard.DASHBOARD_ROUTE
import com.sohaib.animehub.feature.dashboard.DashboardScreen
import com.sohaib.animehub.feature.favourites.FavouriteScreen
import com.sohaib.animehub.feature.home.HomeScreen
import com.sohaib.animehub.feature.settings.SettingScreen
import com.sohaib.animehub.feature.splash.SPLASH_ROUTE
import com.sohaib.animehub.feature.splash.SplashScreen

@Composable
fun NavGraph(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SPLASH_ROUTE,
        modifier = modifier.fillMaxSize(),
        enterTransition = { slideInHorizontally { -it } },
        exitTransition = { slideOutHorizontally { it } }
    ) {

        composable(
            route = SPLASH_ROUTE
        ) {
            SplashScreen(
                navigateToDashboard = {
                    navController.navigate(DASHBOARD_ROUTE) {
                        popUpTo(SPLASH_ROUTE) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = DASHBOARD_ROUTE
        ) {
            DashboardScreen(
                homeContent = { HomeScreen() },
                favouriteContent = { FavouriteScreen() },
                settingContent = { SettingScreen() }
            )
        }
    }
}