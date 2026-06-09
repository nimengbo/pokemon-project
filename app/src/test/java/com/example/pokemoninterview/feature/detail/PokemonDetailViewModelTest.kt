package com.example.pokemoninterview.feature.detail

import com.example.pokemoninterview.core.common.AppResult
import com.example.pokemoninterview.domain.pokemon.model.PokemonAbility
import com.example.pokemoninterview.domain.pokemon.model.PokemonDetail
import com.example.pokemoninterview.domain.pokemon.usecase.FakePokemonRepository
import com.example.pokemoninterview.domain.pokemon.usecase.GetPokemonDetailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonDetailViewModelTest {
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loads detail on init`() = runTest(dispatcher) {
        val repository = FakePokemonRepository().apply {
            detailResult = AppResult.Success(
                PokemonDetail(25, "pikachu", listOf(PokemonAbility(9, "static"))),
            )
        }

        val viewModel = PokemonDetailViewModel("pikachu", GetPokemonDetailUseCase(repository))
        dispatcher.scheduler.advanceUntilIdle()

        assertFalse(viewModel.uiState.value.isLoading)
        assertEquals("pikachu", viewModel.uiState.value.detail?.name)
        assertEquals("static", viewModel.uiState.value.detail?.abilities?.first()?.name)
    }

    @Test
    fun `shows error when detail fails`() = runTest(dispatcher) {
        val repository = FakePokemonRepository().apply {
            detailResult = AppResult.Error("network error")
        }

        val viewModel = PokemonDetailViewModel("missing", GetPokemonDetailUseCase(repository))
        dispatcher.scheduler.advanceUntilIdle()

        assertFalse(viewModel.uiState.value.isLoading)
        assertEquals("network error", viewModel.uiState.value.errorMessage)
    }
}
