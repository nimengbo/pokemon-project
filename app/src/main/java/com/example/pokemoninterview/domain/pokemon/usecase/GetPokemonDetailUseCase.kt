package com.example.pokemoninterview.domain.pokemon.usecase

import com.example.pokemoninterview.core.common.AppResult
import com.example.pokemoninterview.domain.pokemon.model.PokemonDetail
import com.example.pokemoninterview.domain.pokemon.repository.PokemonRepository

class GetPokemonDetailUseCase(
    private val repository: PokemonRepository,
) {
    suspend operator fun invoke(name: String): AppResult<PokemonDetail> {
        val normalizedName = name.trim()
        if (normalizedName.isEmpty()) {
            return AppResult.Error("Pokémon name is required")
        }
        return repository.getPokemonDetail(normalizedName)
    }
}
