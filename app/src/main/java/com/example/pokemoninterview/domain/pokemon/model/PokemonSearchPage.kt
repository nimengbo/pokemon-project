package com.example.pokemoninterview.domain.pokemon.model

data class PokemonSearchPage(
    val items: List<PokemonSpecies>,
    val limit: Int,
    val offset: Int,
    val totalCount: Int,
    val hasMore: Boolean,
)
