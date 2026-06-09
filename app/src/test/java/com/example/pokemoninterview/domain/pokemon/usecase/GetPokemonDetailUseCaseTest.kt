package com.example.pokemoninterview.domain.pokemon.usecase

import com.example.pokemoninterview.core.common.AppResult
import com.example.pokemoninterview.domain.pokemon.model.PokemonDetail
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GetPokemonDetailUseCaseTest {
    @Test
    fun `trims pokemon name before loading detail`() = kotlinx.coroutines.test.runTest {
        val repository = FakePokemonRepository().apply {
            detailResult = AppResult.Success(PokemonDetail(id = 25, name = "pikachu", abilities = emptyList()))
        }
        val useCase = GetPokemonDetailUseCase(repository)

        useCase(" pikachu ")

        assertEquals("pikachu", repository.lastDetailName)
    }

    @Test
    fun `empty pokemon name returns error`() = kotlinx.coroutines.test.runTest {
        val repository = FakePokemonRepository()
        val useCase = GetPokemonDetailUseCase(repository)

        val result = useCase(" ")

        assertTrue(result is AppResult.Error)
        assertEquals(null, repository.lastDetailName)
    }
}
