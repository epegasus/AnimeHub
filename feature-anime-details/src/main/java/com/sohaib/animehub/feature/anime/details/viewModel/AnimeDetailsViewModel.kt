package com.sohaib.animehub.feature.anime.details.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sohaib.animehub.core.common.constants.Constants.TAG
import com.sohaib.animehub.domain.useCases.GetAnimeDetailsByIdUseCase
import com.sohaib.animehub.feature.anime.details.effect.AnimeDetailsEffect
import com.sohaib.animehub.feature.anime.details.intent.AnimeDetailsIntent
import com.sohaib.animehub.feature.anime.details.state.AnimeDetailsState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AnimeDetailsViewModel(private val useCase: GetAnimeDetailsByIdUseCase) : ViewModel() {

    private val _state = MutableStateFlow(AnimeDetailsState())
    val state: StateFlow<AnimeDetailsState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<AnimeDetailsEffect>()
    val effect: SharedFlow<AnimeDetailsEffect> = _effect.asSharedFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleError(throwable)
    }

    fun handleIntent(intent: AnimeDetailsIntent) = viewModelScope.launch(coroutineExceptionHandler) {
        when (intent) {
            is AnimeDetailsIntent.GetData -> getData(intent.animeId)
            AnimeDetailsIntent.OnNavigateBackClick -> _effect.emit(AnimeDetailsEffect.NavigateBack)
        }
    }

    private suspend fun getData(animeId: String) {
        if (animeId.isBlank()) {
            _state.update { it.copy(isLoading = false, isEmpty = true) }
            return
        }

        _state.update { it.copy(isLoading = true, isError = false, isEmpty = false) }

        useCase(animeId)
            .filter { it != null }
            .catch { handleError(it) }
            .collect { animeDetail ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        isError = false,
                        isEmpty = false,
                        animeDetail = animeDetail,
                    )
                }
            }
    }

    private fun handleError(throwable: Throwable) = viewModelScope.launch {
        Log.e(TAG, "AnimeDetailsViewModel: handleError: ", throwable)
        _state.update { it.copy(isLoading = false, isError = true, isEmpty = false) }
    }
}