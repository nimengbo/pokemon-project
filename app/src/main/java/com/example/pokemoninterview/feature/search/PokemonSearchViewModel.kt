package com.example.pokemoninterview.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.pokemoninterview.core.common.AppResult
import com.example.pokemoninterview.domain.pokemon.usecase.SearchPokemonSpeciesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PokemonSearchViewModel(
    private val searchPokemonSpecies: SearchPokemonSpeciesUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(PokemonSearchUiState())
    val uiState: StateFlow<PokemonSearchUiState> = _uiState.asStateFlow()

    fun onQueryChanged(query: String) {
        _uiState.update { it.copy(query = query) }
    }

    fun onSearchClicked() {
        val state = _uiState.value
        if (!state.isSearchEnabled) return
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    items = emptyList(),
                    isInitialLoading = true,
                    errorMessage = null,
                    nextOffset = 0,
                    hasMore = false,
                    hasSearched = true,
                )
            }
            when (val result = searchPokemonSpecies(state.query, state.pageSize, 0)) {
                is AppResult.Success -> _uiState.update {
                    it.copy(
                        items = result.data.items,
                        isInitialLoading = false,
                        nextOffset = result.data.offset + result.data.items.size,
                        hasMore = result.data.hasMore,
                    )
                }
                is AppResult.Error -> _uiState.update {
                    it.copy(isInitialLoading = false, errorMessage = result.message)
                }
            }
        }
    }

    fun loadNextPage() {
        val state = _uiState.value
        if (!state.hasMore || state.isInitialLoading || state.isLoadingMore || state.query.isBlank()) return
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingMore = true, errorMessage = null) }
            when (val result = searchPokemonSpecies(state.query, state.pageSize, state.nextOffset)) {
                is AppResult.Success -> _uiState.update {
                    it.copy(
                        items = it.items + result.data.items,
                        isLoadingMore = false,
                        nextOffset = result.data.offset + result.data.items.size,
                        hasMore = result.data.hasMore,
                    )
                }
                is AppResult.Error -> _uiState.update {
                    it.copy(isLoadingMore = false, errorMessage = result.message)
                }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    class Factory(
        private val searchPokemonSpecies: SearchPokemonSpeciesUseCase,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PokemonSearchViewModel(searchPokemonSpecies) as T
        }
    }
}
