package com.example.pokemoninterview.feature.detail

import com.example.pokemoninterview.domain.pokemon.model.PokemonDetail

data class PokemonDetailUiState(
    val pokemonName: String,
    val detail: PokemonDetail? = null,
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
)
