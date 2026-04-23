package com.sohaib.animehub.feature.favourites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sohaib.animehub.core.common.R as commonR

const val FAVOURITE_ROUTE = "favourites"

@Composable
fun FavouriteScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = stringResource(commonR.string.favourites_screen_title))
    }
}
