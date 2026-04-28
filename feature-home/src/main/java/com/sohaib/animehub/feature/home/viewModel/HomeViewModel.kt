package com.sohaib.animehub.feature.home.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sohaib.animehub.core.common.constants.Constants.TAG
import com.sohaib.animehub.domain.useCases.GetAnimeListUseCase
import com.sohaib.animehub.domain.useCases.RefreshAnimeListUseCase
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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getUseCase: GetAnimeListUseCase,
    private val refreshUseCase: RefreshAnimeListUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<HomeEffect>()
    val effect: SharedFlow<HomeEffect> = _effect.asSharedFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleError(throwable)
    }

    init {
        handleIntent(HomeIntent.GetData)
    }

    fun handleIntent(intent: HomeIntent) = viewModelScope.launch(coroutineExceptionHandler) {
        when (intent) {
            HomeIntent.GetData -> getData()
            HomeIntent.RefreshData -> refreshUseCase.invoke()
            is HomeIntent.OnItemClick -> _effect.emit(HomeEffect.NavigateToDetailPage(intent.animeId))
        }
    }

    private suspend fun getData() {
        _state.update { it.copy(isLoading = true, isError = false) }

        getUseCase()
            .catch { handleError(it) }
            .collect { list ->
                if (list.isEmpty()) {
                    _state.update { it.copy(isLoading = false, isEmpty = true) }
                    return@collect
                }
                Log.d(TAG, "HomeViewModel: fetchData: Total Size = ${list.size}")
                _state.update { it.copy(isLoading = false, isEmpty = false, animeList = list) }
            }
    }

    private fun handleError(throwable: Throwable) = viewModelScope.launch {
        Log.e(TAG, "HomeViewModel: handleError: ", throwable)
        _state.update { it.copy(isLoading = false, isEmpty = false, isError = true) }
    }
}