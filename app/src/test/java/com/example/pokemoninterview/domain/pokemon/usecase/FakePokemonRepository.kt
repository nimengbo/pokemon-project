package com.example.pokemoninterview.domain.pokemon.usecase

import com.example.pokemoninterview.core.common.AppResult
import com.example.pokemoninterview.domain.pokemon.model.PokemonDetail
import com.example.pokemoninterview.domain.pokemon.model.PokemonSearchPage
import com.example.pokemoninterview.domain.pokemon.repository.PokemonRepository

class FakePokemonRepository : PokemonRepository {
    var searchResult: AppResult<PokemonSearchPage> = AppResult.Error("not configured")
    var detailResult: AppResult<PokemonDetail> = AppResult.Error("not configured")
    var lastKeyword: String? = null
    var lastLimit: Int? = null
    var lastOffset: Int? = null
    var lastDetailName: String? = null

    override suspend fun searchSpecies(keyword: String, limit: Int, offset: Int): AppResult<PokemonSearchPage> {
        lastKeyword = keyword
        lastLimit = limit
        lastOffset = offset
        return searchResult
    }

    override suspend fun getPokemonDetail(name: String): AppResult<PokemonDetail> {
        lastDetailName = name
        return detailResult
    }
}
