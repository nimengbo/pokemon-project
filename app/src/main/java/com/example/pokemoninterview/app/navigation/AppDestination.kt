package com.example.pokemoninterview.app.navigation

import android.net.Uri

sealed class AppDestination(val route: String) {
    data object Splash : AppDestination("splash")
    data object Search : AppDestination("search")
    data object Detail : AppDestination("detail/{pokemonName}") {
        const val ARG_POKEMON_NAME = "pokemonName"
        fun routeFor(pokemonName: String): String = "detail/${Uri.encode(pokemonName)}"
    }
}
