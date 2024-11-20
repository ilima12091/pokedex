package com.example.pokedex

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pokedex.ui.screens.HomeScreen


@Composable
fun OurNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String
) {
    NavHost(navController, startDestination, modifier = modifier) {
        composable("PokemonListScreen") {
            HomeScreen (
                onPokemonNameClick = { pokemonName ->
                    navController.navigate("PokemonDetailsScreen/${pokemonName}")
                }
            )
        }
        composable("PokemonDetailsScreen/{pokemonName}") { backStackEntry ->
            val pokemonName = backStackEntry.arguments?.getString("pokemonName")
            pokemonName?.let { PokemonDetailsScreen(pokemonName= it, navController = navController) }
        }
    }
}