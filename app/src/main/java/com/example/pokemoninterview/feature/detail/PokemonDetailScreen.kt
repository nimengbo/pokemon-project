package com.example.pokemoninterview.feature.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
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

@Composable
fun PokemonDetailScreen(
    viewModel: PokemonDetailViewModel,
    onBackClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val detail = uiState.detail
    val errorMessage = uiState.errorMessage
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row {
            Button(onClick = onBackClick) {
                Text(text = "Back")
            }
        }
        when {
            uiState.isLoading -> LoadingContent(message = "Loading ${uiState.pokemonName}...")
            errorMessage != null -> ErrorContent(
                message = errorMessage,
                onDismiss = viewModel::loadDetail,
            )
            detail != null -> ElevatedCard {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text(
                        text = detail.name.replaceFirstChar { it.titlecase() },
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(text = "Abilities", fontWeight = FontWeight.SemiBold)
                    detail.abilities.forEach { ability ->
                        Text(text = "• ${ability.name}")
                    }
                }
            }
        }
    }
}
