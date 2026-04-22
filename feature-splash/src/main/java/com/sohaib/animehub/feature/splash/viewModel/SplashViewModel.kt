package com.sohaib.animehub.feature.splash.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sohaib.animehub.core.common.constants.Constants.TAG
import com.sohaib.animehub.feature.splash.effect.SplashEffect
import com.sohaib.animehub.feature.splash.intent.SplashIntent
import com.sohaib.animehub.feature.splash.state.SplashState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private val _state = MutableStateFlow(SplashState())
    val state: StateFlow<SplashState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<SplashEffect>()
    val effect: SharedFlow<SplashEffect> = _effect.asSharedFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleError(throwable)
    }

    init {
        handleIntent(SplashIntent.StartTimer)
    }

    private fun handleIntent(intent: SplashIntent) = viewModelScope.launch(coroutineExceptionHandler) {
        when (intent) {
            SplashIntent.StartTimer -> startTimer()
        }
    }

    private suspend fun startTimer() {
        _state.update { it.copy(isLoading = true) }

        delay(1000)

        _state.update { it.copy(isLoading = false) }
        _effect.emit(SplashEffect.NavigateToDashboard)
    }

    private fun handleError(throwable: Throwable) = viewModelScope.launch {
        Log.e(TAG, "SplashViewModel: handleError: ", throwable)
    }
}
