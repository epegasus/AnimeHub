package com.sohaib.animehub.feature.home.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sohaib.animehub.core.common.constants.Constants.TAG
import com.sohaib.animehub.domain.errors.DomainError
import com.sohaib.animehub.domain.useCases.GetAnimeListUseCase
import com.sohaib.animehub.domain.useCases.RefreshAnimeListUseCase
import com.sohaib.animehub.feature.home.effect.HomeEffect
import com.sohaib.animehub.feature.home.intent.HomeIntent
import com.sohaib.animehub.feature.home.state.HomeState
import com.sohaib.animehub.feature.home.state.HomeUiState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import com.sohaib.animehub.core.common.R as commonR

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
    private var observeJob: Job? = null

    init {
        handleIntent(HomeIntent.GetData)
    }

    fun handleIntent(intent: HomeIntent) = viewModelScope.launch(coroutineExceptionHandler) {
        when (intent) {
            HomeIntent.GetData -> getData()
            HomeIntent.RefreshData -> refreshAnimeList()
            is HomeIntent.OnItemClick -> _effect.emit(HomeEffect.NavigateToDetailPage(intent.animeId))
        }
    }

    private suspend fun getData() {
        observeAnimeList()
        refreshAnimeList()
    }

    private fun observeAnimeList() {
        if (observeJob != null) return

        observeJob = viewModelScope.launch(coroutineExceptionHandler) {
            getUseCase()
                .catch { handleError(it) }
                .collect { list ->
                    if (list.isEmpty()) {
                        _state.update { current ->
                            when (current.hasCompletedInitialSync) {
                                true -> current.copy(uiState = HomeUiState.Empty)
                                false -> current.copy(uiState = HomeUiState.Loading)
                            }
                        }
                        return@collect
                    }
                    Log.d(TAG, "HomeViewModel: observeAnimeList: Total Size = ${list.size}")
                    _state.update { it.copy(uiState = HomeUiState.Success(list)) }
                }
        }
    }

    private suspend fun refreshAnimeList() {
        val hasContent = _state.value.uiState is HomeUiState.Success
        when (hasContent) {
            true -> _state.update { it.copy(isRefreshing = true) }
            false -> _state.update { it.copy(uiState = HomeUiState.Loading) }
        }

        try {
            refreshUseCase()
        } catch (throwable: Throwable) {
            handleError(throwable)
        } finally {
            _state.update { it.copy(isRefreshing = false, hasCompletedInitialSync = true) }
        }
    }

    private fun handleError(throwable: Throwable) = viewModelScope.launch {
        Log.e(TAG, "HomeViewModel: handleError: ", throwable)
        if (_state.value.uiState !is HomeUiState.Success) {
            _state.update { it.copy(uiState = HomeUiState.Error(throwable.toUiMessageRes()), hasCompletedInitialSync = true) }
        }
    }

    private fun Throwable.toUiMessageRes(): Int = when (this) {
        is DomainError.Server -> commonR.string.error_server
        is DomainError.Client -> commonR.string.error_client
        is DomainError.Unknown -> commonR.string.error_unknown
        is DomainError.Network, is UnknownHostException, is ConnectException, is SocketTimeoutException -> commonR.string.error_network
        else -> commonR.string.error_unknown
    }
}