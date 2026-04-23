package com.sohaib.animehub.feature.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sohaib.animehub.core.common.R as commonR

const val SETTING_ROUTE = "setting"

@Composable
fun SettingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = stringResource(commonR.string.setting_screen_title))
    }
}
