package com.example.pokemoninterview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.pokemoninterview.app.navigation.AppNavGraph
import com.example.pokemoninterview.core.ui.theme.PokemonInterviewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appContainer = (application as PokemonApplication).appContainer
        setContent {
            PokemonInterviewTheme {
                AppNavGraph(
                    navController = rememberNavController(),
                    appContainer = appContainer,
                )
            }
        }
    }
}
