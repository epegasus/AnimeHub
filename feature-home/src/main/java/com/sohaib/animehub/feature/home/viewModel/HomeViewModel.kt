package com.sohaib.animehub.feature.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.sohaib.animehub.domain.useCases.GetAnimeListUseCase
import com.sohaib.animehub.feature.home.effect.HomeEffect
import com.sohaib.animehub.feature.home.intent.HomeIntent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    getAnimeListUseCase: GetAnimeListUseCase,
) : ViewModel() {

    val animePagingFlow = getAnimeListUseCase().cachedIn(viewModelScope)

    private val _effect = MutableSharedFlow<HomeEffect>()
    val effect: SharedFlow<HomeEffect> = _effect.asSharedFlow()

    fun handleIntent(intent: HomeIntent) = viewModelScope.launch {
        when (intent) {
            is HomeIntent.OnItemClick -> _effect.emit(HomeEffect.NavigateToDetailPage(intent.animeId))
        }
    }
}