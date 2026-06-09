package com.example.pokemoninterview.data.pokemon.remote

import com.apollographql.apollo.ApolloClient
import com.example.pokemoninterview.graphql.GetPokemonDetailQuery
import com.example.pokemoninterview.graphql.SearchPokemonSpeciesQuery

class ApolloPokemonRemoteDataSource(
    private val apolloClient: ApolloClient,
) : PokemonRemoteDataSource {
    override suspend fun searchSpecies(keyword: String, limit: Int, offset: Int): SearchPokemonSpeciesQuery.Data {
        val response = apolloClient.query(SearchPokemonSpeciesQuery(keyword, limit, offset)).execute()
        response.exception?.let { throw it }
        if (response.hasErrors()) {
            throw IllegalStateException(response.errors.orEmpty().joinToString { it.message })
        }
        return requireNotNull(response.data) { "Search response data is empty" }
    }

    override suspend fun getPokemonDetail(name: String): GetPokemonDetailQuery.Data {
        val response = apolloClient.query(GetPokemonDetailQuery(name)).execute()
        response.exception?.let { throw it }
        if (response.hasErrors()) {
            throw IllegalStateException(response.errors.orEmpty().joinToString { it.message })
        }
        return requireNotNull(response.data) { "Detail response data is empty" }
    }
}
