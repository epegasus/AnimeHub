package com.sohaib.animehub.feature.dashboard

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sohaib.animehub.feature.dashboard.model.BottomNavItem
import com.sohaib.animehub.core.common.R as commonR

const val DASHBOARD_ROUTE = "dashboard"
const val DASHBOARD_HOME_ROUTE = "dashboard/home"
const val DASHBOARD_FAVOURITE_ROUTE = "dashboard/favourites"
const val DASHBOARD_SETTING_ROUTE = "dashboard/setting"

private val bottomNavItems = listOf(
    BottomNavItem(DASHBOARD_HOME_ROUTE, Icons.Rounded.Home, commonR.string.home),
    BottomNavItem(DASHBOARD_FAVOURITE_ROUTE, Icons.Rounded.Favorite, commonR.string.favourites),
    BottomNavItem(DASHBOARD_SETTING_ROUTE, Icons.Rounded.Settings, commonR.string.settings),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    homeContent: @Composable () -> Unit,
    favouriteContent: @Composable () -> Unit,
    settingContent: @Composable () -> Unit,
) {
    val navController = rememberNavController()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(commonR.string.app_name),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route.orEmpty()
            ) { route ->
                navController.navigate(route) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    ) { innerPadding ->

        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            navController = navController,
            startDestination = DASHBOARD_HOME_ROUTE
        ) {
            composable(DASHBOARD_HOME_ROUTE) { homeContent() }
            composable(DASHBOARD_FAVOURITE_ROUTE) { favouriteContent() }
            composable(DASHBOARD_SETTING_ROUTE) { settingContent() }
        }
    }
}

@Composable
fun BottomAppBar(
    modifier: Modifier = Modifier,
    currentRoute: String,
    onClicked: (String) -> Unit,
) {
    NavigationBar(
        modifier = modifier
    ) {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                label = { Text(text = stringResource(item.label)) },
                icon = { Icon(imageVector = item.icon, contentDescription = stringResource(item.label)) },
                selected = currentRoute == item.route,
                onClick = { onClicked.invoke(item.route) }
            )
        }
    }
}
