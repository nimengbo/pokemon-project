package com.example.pokemoninterview.domain.pokemon.usecase

import com.example.pokemoninterview.core.common.AppResult
import com.example.pokemoninterview.domain.pokemon.model.PokemonSearchPage
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SearchPokemonSpeciesUseCaseTest {
    @Test
    fun `trims query and wraps keyword for fuzzy search`() = kotlinx.coroutines.test.runTest {
        val repository = FakePokemonRepository().apply {
            searchResult = AppResult.Success(PokemonSearchPage(emptyList(), limit = 20, offset = 0, hasMore = false))
        }
        val useCase = SearchPokemonSpeciesUseCase(repository)

        useCase(" pika ", limit = 20, offset = 0)

        assertEquals("%pika%", repository.lastKeyword)
        assertEquals(20, repository.lastLimit)
        assertEquals(0, repository.lastOffset)
    }

    @Test
    fun `empty query returns error without repository call`() = kotlinx.coroutines.test.runTest {
        val repository = FakePokemonRepository()
        val useCase = SearchPokemonSpeciesUseCase(repository)

        val result = useCase("   ", limit = 20, offset = 0)

        assertTrue(result is AppResult.Error)
        assertEquals(null, repository.lastKeyword)
    }
}
