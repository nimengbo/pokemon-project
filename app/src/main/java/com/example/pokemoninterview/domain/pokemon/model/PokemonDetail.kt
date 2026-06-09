package com.example.pokemoninterview.domain.pokemon.model

data class PokemonDetail(
    val id: Int,
    val name: String,
    val abilities: List<PokemonAbility>,
)
