package com.example.pokedex.ui.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokedex.AuthActivity
import com.example.pokedex.ui.screens.HomeScreen
import com.example.pokedex.ui.screens.PokemonDetailsScreen
import com.example.pokedex.ui.screens.PokemonListScreen
import com.example.pokedex.ui.screens.ProfileScreen

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    onNavigateToLogin: () -> Unit,
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = "HomeScreen"
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
        composable(
            "ProfileScreen"
        ) {
            ProfileScreen(
                onSignOut = { onNavigateToLogin() }
            )
        }
        composable("PokemonListScreen") {
            PokemonListScreen (
                onPokemonNameClick = { pokemonName ->
                    navController.navigate("PokemonDetailsScreen/${pokemonName}")
                }
            )
        }
        composable("HomeScreen") {
            HomeScreen (
                onNavigateToPokemonList = {navController.navigate("PokemonListScreen")}
            )
        }
    }
}