package com.example.pokemoninterview.app.di

import com.example.pokemoninterview.core.common.AppDispatchers
import com.example.pokemoninterview.core.network.ApolloClientFactory
import com.example.pokemoninterview.data.pokemon.remote.ApolloPokemonRemoteDataSource
import com.example.pokemoninterview.data.pokemon.repository.PokemonRepositoryImpl
import com.example.pokemoninterview.domain.pokemon.usecase.GetPokemonDetailUseCase
import com.example.pokemoninterview.domain.pokemon.usecase.SearchPokemonSpeciesUseCase

class AppContainer {
    private val dispatchers = AppDispatchers()
    private val apolloClient = ApolloClientFactory.create()
    private val pokemonRemoteDataSource = ApolloPokemonRemoteDataSource(apolloClient)
    private val pokemonRepository = PokemonRepositoryImpl(pokemonRemoteDataSource, dispatchers)

    val searchPokemonSpeciesUseCase = SearchPokemonSpeciesUseCase(pokemonRepository)
    val getPokemonDetailUseCase = GetPokemonDetailUseCase(pokemonRepository)
}
