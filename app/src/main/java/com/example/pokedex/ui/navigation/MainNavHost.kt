package com.example.pokedex.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokedex.ui.screens.PokemonDetailsScreen

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = "PokemonDetailsScreen/4"
    ) {
        composable(
            "PokemonDetailsScreen/{pokemonId}",
            arguments = listOf(navArgument("pokemonId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            PokemonDetailsScreen(
                pokemonId = backStackEntry.arguments?.getString("pokemonId") ?: "",
                onNavigateToPokemon = { pokemonId ->
                    navController.navigate("PokemonDetailsScreen/$pokemonId")
                },
                onGoBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}