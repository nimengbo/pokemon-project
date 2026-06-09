package com.example.pokemoninterview.feature.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pokemoninterview.core.ui.component.ErrorContent
import com.example.pokemoninterview.core.ui.component.LoadingContent
import com.example.pokemoninterview.core.ui.component.PaginationFooter
import com.example.pokemoninterview.feature.search.component.PokemonSpeciesCard
import com.example.pokemoninterview.feature.search.component.SearchInputBar

@Composable
fun PokemonSearchScreen(
    viewModel: PokemonSearchViewModel,
    onPokemonClick: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = "Pokémon Species Search",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )
        SearchInputBar(
            query = uiState.query,
            isSearchEnabled = uiState.isSearchEnabled,
            onQueryChanged = viewModel::onQueryChanged,
            onSearchClicked = viewModel::onSearchClicked,
        )
        uiState.errorMessage?.let { message ->
            ErrorContent(message = message, onDismiss = viewModel::clearError)
        }
        when {
            uiState.isInitialLoading -> LoadingContent(message = "Searching Pokémon...")
            uiState.hasSearched && uiState.items.isEmpty() -> Text(text = "No species found")
            else -> LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                itemsIndexed(uiState.items, key = { _, item -> item.id }) { index, species ->
                    PokemonSpeciesCard(species = species, onPokemonClick = onPokemonClick)
                    if (index >= uiState.items.lastIndex - 2) {
                        viewModel.loadNextPage()
                    }
                }
                if (uiState.hasSearched && uiState.items.isNotEmpty()) {
                    item {
                        PaginationFooter(isLoading = uiState.isLoadingMore, hasMore = uiState.hasMore)
                    }
                }
            }
        }
    }
}
