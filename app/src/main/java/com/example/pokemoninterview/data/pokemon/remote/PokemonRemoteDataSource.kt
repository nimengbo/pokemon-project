package com.example.pokemoninterview.data.pokemon.remote

import com.example.pokemoninterview.graphql.GetPokemonDetailQuery
import com.example.pokemoninterview.graphql.SearchPokemonSpeciesQuery

interface PokemonRemoteDataSource {
    suspend fun searchSpecies(keyword: String, limit: Int, offset: Int): SearchPokemonSpeciesQuery.Data
    suspend fun getPokemonDetail(name: String): GetPokemonDetailQuery.Data
}
