package com.example.pokemoninterview.app.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.pokemoninterview.app.di.AppContainer
import com.example.pokemoninterview.feature.detail.PokemonDetailScreen
import com.example.pokemoninterview.feature.detail.PokemonDetailViewModel
import com.example.pokemoninterview.feature.search.PokemonSearchScreen
import com.example.pokemoninterview.feature.search.PokemonSearchViewModel
import com.example.pokemoninterview.feature.splash.SplashScreen

@Composable
fun AppNavGraph(navController: NavHostController, appContainer: AppContainer) {
    NavHost(navController = navController, startDestination = AppDestination.Splash.route) {
        composable(AppDestination.Splash.route) {
            SplashScreen(
                onFinished = {
                    navController.navigate(AppDestination.Search.route) {
                        popUpTo(AppDestination.Splash.route) { inclusive = true }
                    }
                },
            )
        }
        composable(AppDestination.Search.route) {
            val searchViewModel: PokemonSearchViewModel = viewModel(
                factory = PokemonSearchViewModel.Factory(appContainer.searchPokemonSpeciesUseCase),
            )
            PokemonSearchScreen(
                viewModel = searchViewModel,
                onPokemonClick = { name -> navController.navigate(AppDestination.Detail.routeFor(name)) },
            )
        }
        composable(
            route = AppDestination.Detail.route,
            arguments = listOf(navArgument(AppDestination.Detail.ARG_POKEMON_NAME) { type = NavType.StringType }),
        ) { backStackEntry ->
            val pokemonName = backStackEntry.arguments?.getString(AppDestination.Detail.ARG_POKEMON_NAME).orEmpty()
            val detailViewModel: PokemonDetailViewModel = viewModel(
                key = "detail-$pokemonName",
                factory = PokemonDetailViewModel.Factory(pokemonName, appContainer.getPokemonDetailUseCase),
            )
            PokemonDetailScreen(
                viewModel = detailViewModel,
                onBackClick = { navController.popBackStack() },
            )
        }
    }
}
