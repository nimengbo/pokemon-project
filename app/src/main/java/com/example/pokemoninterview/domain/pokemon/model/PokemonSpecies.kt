package com.example.pokemoninterview.domain.pokemon.model

data class PokemonSpecies(
    val id: Int,
    val name: String,
    val captureRate: Int,
    val colorName: String,
    val pokemonNames: List<String>,
)
