package com.example.pokedex

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pokedex.ui.screens.HomeScreen
import com.example.pokedex.ui.screens.PokemonDetailsScreen
import com.example.pokedex.ui.screens.PokemonListScreen


@Composable
fun OurNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String
) {
    NavHost(navController, startDestination, modifier = modifier) {
        composable("PokemonListScreen") {
            PokemonListScreen (
                onPokemonNameClick = { pokemonName ->
                    navController.navigate("PokemonDetailsScreen/${pokemonName}")
                }
            )
        }
        composable("PokemonDetailsScreen/{pokemonName}") { backStackEntry ->
            val pokemonName = backStackEntry.arguments?.getString("pokemonName")
            pokemonName?.let { PokemonDetailsScreen(pokemonId= it, navController = navController) }
        }
    }
}