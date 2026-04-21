package com.sohaib.animehub.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sohaib.animehub.feature_splash.SPLASH_ROUTE
import com.sohaib.animehub.feature_splash.SplashScreen

@Composable
fun MainNavGraph(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SPLASH_ROUTE,
        modifier = modifier.fillMaxSize()
    ) {
        composable(route = SPLASH_ROUTE) {
            SplashScreen()
        }
    }
}