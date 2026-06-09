package com.example.pokemoninterview

import android.app.Application
import com.example.pokemoninterview.app.di.AppContainer

class PokemonApplication : Application() {
    val appContainer: AppContainer by lazy { AppContainer() }
}
