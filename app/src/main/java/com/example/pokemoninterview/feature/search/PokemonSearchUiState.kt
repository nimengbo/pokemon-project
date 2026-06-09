package com.example.pokemoninterview.feature.search

import com.example.pokemoninterview.domain.pokemon.model.PokemonSpecies

data class PokemonSearchUiState(
    val query: String = "",
    val items: List<PokemonSpecies> = emptyList(),
    val isInitialLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val errorMessage: String? = null,
    val pageSize: Int = 20,
    val nextOffset: Int = 0,
    val hasMore: Boolean = false,
    val hasSearched: Boolean = false,
) {
    val isSearchEnabled: Boolean = query.isNotBlank() && !isInitialLoading
}
