package com.sohaib.animehub.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sohaib.animehub.feature_dashboard.DASHBOARD_ROUTE
import com.sohaib.animehub.feature_dashboard.DashboardScreen
import com.sohaib.animehub.feature_splash.SPLASH_ROUTE
import com.sohaib.animehub.feature_splash.SplashScreen

@Composable
fun NavGraph(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SPLASH_ROUTE,
        modifier = modifier.fillMaxSize(),
        enterTransition = { slideInHorizontally { -1 } },
        exitTransition = { slideOutHorizontally { 1 } }
    ) {

        composable(
            route = SPLASH_ROUTE
        ) {
            SplashScreen(
                navigateToDashboard = { navController.navigate(DASHBOARD_ROUTE) }
            )
        }

        composable(
            route = DASHBOARD_ROUTE
        ) {
            DashboardScreen()
        }
    }
}