package com.sohaib.animehub.feature.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sohaib.animehub.core.common.R
import com.sohaib.animehub.feature.splash.effect.SplashEffect
import com.sohaib.animehub.feature.splash.state.SplashState
import com.sohaib.animehub.feature.splash.viewModel.SplashViewModel

const val SPLASH_ROUTE = "splash"

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navigateToDashboard: () -> Unit,
    viewModel: SplashViewModel = viewModel(),
) {

    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { splashEffect ->
            when (splashEffect) {
                SplashEffect.NavigateToDashboard -> navigateToDashboard()
            }
        }
    }

    ScreenScreenContent(modifier, state.value)
}

@Composable
private fun ScreenScreenContent(
    modifier: Modifier = Modifier,
    state: SplashState,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primary
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineLarge
            )
            if (state.isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Preview
@Composable
private fun SplashScreenPreview() {
    SplashScreen(
        navigateToDashboard = {}
    )
}