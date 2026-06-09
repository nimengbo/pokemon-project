package com.example.pokemoninterview.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.pokemoninterview.core.common.AppResult
import com.example.pokemoninterview.domain.pokemon.usecase.GetPokemonDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PokemonDetailViewModel(
    pokemonName: String,
    private val getPokemonDetail: GetPokemonDetailUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(PokemonDetailUiState(pokemonName = pokemonName))
    val uiState: StateFlow<PokemonDetailUiState> = _uiState.asStateFlow()

    init {
        loadDetail()
    }

    fun loadDetail() {
        val pokemonName = _uiState.value.pokemonName
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            when (val result = getPokemonDetail(pokemonName)) {
                is AppResult.Success -> _uiState.update {
                    it.copy(detail = result.data, isLoading = false)
                }
                is AppResult.Error -> _uiState.update {
                    it.copy(isLoading = false, errorMessage = result.message)
                }
            }
        }
    }

    class Factory(
        private val pokemonName: String,
        private val getPokemonDetail: GetPokemonDetailUseCase,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PokemonDetailViewModel(pokemonName, getPokemonDetail) as T
        }
    }
}
