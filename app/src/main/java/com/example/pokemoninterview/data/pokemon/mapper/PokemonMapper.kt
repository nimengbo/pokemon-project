package com.example.pokemoninterview.data.pokemon.mapper

import com.example.pokemoninterview.domain.pokemon.model.PokemonAbility
import com.example.pokemoninterview.domain.pokemon.model.PokemonDetail
import com.example.pokemoninterview.domain.pokemon.model.PokemonSearchPage
import com.example.pokemoninterview.domain.pokemon.model.PokemonSpecies
import com.example.pokemoninterview.graphql.GetPokemonDetailQuery
import com.example.pokemoninterview.graphql.SearchPokemonSpeciesQuery

object PokemonMapper {
    fun mapSearchPage(data: SearchPokemonSpeciesQuery.Data, limit: Int, offset: Int): PokemonSearchPage {
        val items = data.pokemon_v2_pokemonspecies.map { species ->
            PokemonSpecies(
                id = species.id,
                name = species.name,
                captureRate = species.capture_rate ?: 0,
                colorName = species.pokemon_v2_pokemoncolor?.name ?: "unknown",
                pokemonNames = species.pokemon_v2_pokemons.map { it.name },
            )
        }
        val totalCount = data.pokemon_v2_pokemonspecies_aggregate.aggregate?.count ?: items.size
        return PokemonSearchPage(
            items = items,
            limit = limit,
            offset = offset,
            totalCount = totalCount,
            hasMore = offset + items.size < totalCount,
        )
    }

    fun mapDetail(data: GetPokemonDetailQuery.Data): PokemonDetail? {
        val pokemon = data.pokemon_v2_pokemon.firstOrNull() ?: return null
        return PokemonDetail(
            id = pokemon.id,
            name = pokemon.name,
            abilities = pokemon.pokemon_v2_pokemonabilities.mapNotNull { abilitySlot ->
                abilitySlot.pokemon_v2_ability?.let { ability ->
                    PokemonAbility(id = ability.id, name = ability.name)
                }
            },
        )
    }
}
