package com.example.pokemoninterview.domain.pokemon.repository

import com.example.pokemoninterview.core.common.AppResult
import com.example.pokemoninterview.domain.pokemon.model.PokemonDetail
import com.example.pokemoninterview.domain.pokemon.model.PokemonSearchPage

interface PokemonRepository {
    suspend fun searchSpecies(keyword: String, limit: Int, offset: Int): AppResult<PokemonSearchPage>
    suspend fun getPokemonDetail(name: String): AppResult<PokemonDetail>
}
