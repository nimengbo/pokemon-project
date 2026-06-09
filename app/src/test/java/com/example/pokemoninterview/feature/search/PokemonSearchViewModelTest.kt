package com.example.pokemoninterview.feature.search

import com.example.pokemoninterview.core.common.AppResult
import com.example.pokemoninterview.domain.pokemon.model.PokemonSearchPage
import com.example.pokemoninterview.domain.pokemon.model.PokemonSpecies
import com.example.pokemoninterview.domain.pokemon.usecase.FakePokemonRepository
import com.example.pokemoninterview.domain.pokemon.usecase.SearchPokemonSpeciesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonSearchViewModelTest {
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
    fun `blank query disables search`() {
        val viewModel = createViewModel(FakePokemonRepository())

        viewModel.onQueryChanged("   ")

        assertFalse(viewModel.uiState.value.isSearchEnabled)
    }

    @Test
    fun `search success updates items and next offset`() = runTest(dispatcher) {
        val repository = FakePokemonRepository().apply {
            searchResult = AppResult.Success(
                PokemonSearchPage(
                    items = listOf(PokemonSpecies(25, "pikachu", 190, "yellow", listOf("pikachu"))),
                    limit = 20,
                    offset = 0,
                    hasMore = false,
                ),
            )
        }
        val viewModel = createViewModel(repository)

        viewModel.onQueryChanged("pika")
        viewModel.onSearchClicked()
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(1, viewModel.uiState.value.items.size)
        assertEquals(1, viewModel.uiState.value.nextOffset)
        assertTrue(viewModel.uiState.value.hasSearched)
    }

    @Test
    fun `load next page appends items`() = runTest(dispatcher) {
        val repository = FakePokemonRepository().apply {
            searchResult = AppResult.Success(
                PokemonSearchPage(
                    items = List(20) { PokemonSpecies(it, "species-$it", 1, "blue", listOf("pokemon-$it")) },
                    limit = 20,
                    offset = 0,
                    hasMore = true,
                ),
            )
        }
        val viewModel = createViewModel(repository)
        viewModel.onQueryChanged("poke")
        viewModel.onSearchClicked()
        dispatcher.scheduler.advanceUntilIdle()
        repository.searchResult = AppResult.Success(
            PokemonSearchPage(
                items = listOf(PokemonSpecies(99, "next", 1, "red", listOf("next"))),
                limit = 20,
                offset = 20,
                hasMore = false,
            ),
        )

        viewModel.loadNextPage()
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(21, viewModel.uiState.value.items.size)
        assertEquals(21, viewModel.uiState.value.nextOffset)
        assertFalse(viewModel.uiState.value.hasMore)
    }

    private fun createViewModel(repository: FakePokemonRepository): PokemonSearchViewModel {
        return PokemonSearchViewModel(SearchPokemonSpeciesUseCase(repository))
    }
}
