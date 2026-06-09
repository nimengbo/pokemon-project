package com.example.pokemoninterview.data.pokemon.repository

import com.example.pokemoninterview.core.common.AppDispatchers
import com.example.pokemoninterview.core.common.AppResult
import com.example.pokemoninterview.data.pokemon.mapper.PokemonMapper
import com.example.pokemoninterview.data.pokemon.remote.PokemonRemoteDataSource
import com.example.pokemoninterview.domain.pokemon.model.PokemonDetail
import com.example.pokemoninterview.domain.pokemon.model.PokemonSearchPage
import com.example.pokemoninterview.domain.pokemon.repository.PokemonRepository
import kotlinx.coroutines.withContext

class PokemonRepositoryImpl(
    private val remoteDataSource: PokemonRemoteDataSource,
    private val dispatchers: AppDispatchers,
) : PokemonRepository {
    override suspend fun searchSpecies(keyword: String, limit: Int, offset: Int): AppResult<PokemonSearchPage> =
        withContext(dispatchers.io) {
            runCatching {
                PokemonMapper.mapSearchPage(remoteDataSource.searchSpecies(keyword, limit, offset), limit, offset)
            }.fold(
                onSuccess = { AppResult.Success(it) },
                onFailure = { AppResult.Error(it.message ?: "Failed to search Pokémon species", it) },
            )
        }

    override suspend fun getPokemonDetail(name: String): AppResult<PokemonDetail> =
        withContext(dispatchers.io) {
            runCatching {
                PokemonMapper.mapDetail(remoteDataSource.getPokemonDetail(name))
                    ?: throw IllegalArgumentException("Pokémon not found: $name")
            }.fold(
                onSuccess = { AppResult.Success(it) },
                onFailure = { AppResult.Error(it.message ?: "Failed to load Pokémon detail", it) },
            )
        }
}
