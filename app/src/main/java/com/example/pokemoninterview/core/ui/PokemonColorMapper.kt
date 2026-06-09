package com.example.pokemoninterview.core.ui

import androidx.compose.ui.graphics.Color

object PokemonColorMapper {
    fun backgroundFor(colorName: String): Color = when (colorName.lowercase()) {
        "black" -> Color(0xFFBDBDBD)
        "blue" -> Color(0xFFBBDEFB)
        "brown" -> Color(0xFFD7CCC8)
        "gray" -> Color(0xFFE0E0E0)
        "green" -> Color(0xFFC8E6C9)
        "pink" -> Color(0xFFF8BBD0)
        "purple" -> Color(0xFFE1BEE7)
        "red" -> Color(0xFFFFCDD2)
        "white" -> Color(0xFFF5F5F5)
        "yellow" -> Color(0xFFFFF9C4)
        else -> Color(0xFFEDE7F6)
    }
}
