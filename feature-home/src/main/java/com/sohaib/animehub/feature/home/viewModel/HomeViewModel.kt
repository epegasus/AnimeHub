package com.sohaib.animehub.feature.home.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sohaib.animehub.core.common.constants.Constants.TAG
import com.sohaib.animehub.domain.useCases.GetAnimeListUseCase
import com.sohaib.animehub.feature.home.effect.HomeEffect
import com.sohaib.animehub.feature.home.intent.HomeIntent
import com.sohaib.animehub.feature.home.state.HomeState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val useCase: GetAnimeListUseCase) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<HomeEffect>()
    val effect: SharedFlow<HomeEffect> = _effect.asSharedFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleError(throwable)
    }

    init {
        handleIntent(HomeIntent.FetchData)
    }

    fun handleIntent(intent: HomeIntent) = viewModelScope.launch(coroutineExceptionHandler) {
        when (intent) {
            HomeIntent.FetchData -> fetchData()
            HomeIntent.RefreshData -> fetchData()
            is HomeIntent.OnItemClick -> _effect.emit(HomeEffect.NavigateToDetailPage(intent.animeId))
        }
    }

    private suspend fun fetchData() {
        _state.update { it.copy(isLoading = true, isError = false) }

        try {
            val list = useCase()
            if (list.isEmpty()) {
                _state.update { it.copy(isLoading = false, isEmpty = true) }
                return
            }
            Log.d(TAG, "HomeViewModel: fetchData: Total Size = ${list.size}")
            _state.update { it.copy(isLoading = false, isEmpty = false, animeList = list) }
        } catch (ex: Exception) {
            handleError(ex)
        }
    }

    private fun handleError(throwable: Throwable) = viewModelScope.launch {
        Log.e(TAG, "HomeViewModel: handleError: ", throwable)
        _state.update { it.copy(isLoading = false, isEmpty = false, isError = true) }
    }
}