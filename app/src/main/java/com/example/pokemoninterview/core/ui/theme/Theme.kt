package com.example.pokemoninterview.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = PokemonPrimary,
    secondary = PokemonSecondary,
    background = PokemonBackground,
)

@Composable
fun PokemonInterviewTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = PokemonTypography,
        content = content,
    )
}
