package com.example.pokemoninterview.feature.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pokemoninterview.core.ui.PokemonColorMapper
import com.example.pokemoninterview.domain.pokemon.model.PokemonSpecies

@Composable
@OptIn(ExperimentalLayoutApi::class)
fun PokemonSpeciesCard(
    species: PokemonSpecies,
    onPokemonClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(PokemonColorMapper.backgroundFor(species.colorName), RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = species.name.replaceFirstChar { it.titlecase() },
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
        Text(text = "Capture rate: ${species.captureRate}")
        Text(text = "Pokémon", fontWeight = FontWeight.SemiBold)
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            species.pokemonNames.forEach { pokemonName ->
                AssistChip(
                    onClick = { onPokemonClick(pokemonName) },
                    label = { Text(text = pokemonName) },
                )
            }
        }
    }
}
