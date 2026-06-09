package com.example.pokemoninterview.core.network

import com.apollographql.apollo.ApolloClient

object ApolloClientFactory {
    fun create(): ApolloClient = ApolloClient.Builder()
        .serverUrl(GraphQlConfig.POKEAPI_ENDPOINT)
        .build()
}
