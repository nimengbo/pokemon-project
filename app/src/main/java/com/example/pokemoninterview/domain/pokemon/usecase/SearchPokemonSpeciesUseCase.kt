package com.example.pokemoninterview.domain.pokemon.usecase

import com.example.pokemoninterview.core.common.AppResult
import com.example.pokemoninterview.domain.pokemon.model.PokemonSearchPage
import com.example.pokemoninterview.domain.pokemon.repository.PokemonRepository

class SearchPokemonSpeciesUseCase(
    private val repository: PokemonRepository,
) {
    suspend operator fun invoke(query: String, limit: Int, offset: Int): AppResult<PokemonSearchPage> {
        val normalizedQuery = query.trim()
        if (normalizedQuery.isEmpty()) {
            return AppResult.Error("Please enter a Pokémon species name")
        }
        return repository.searchSpecies(keyword = "%$normalizedQuery%", limit = limit, offset = offset)
    }
}
