package com.sohaib.animehub.feature_dashboard.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    @param:StringRes val label: Int,
)