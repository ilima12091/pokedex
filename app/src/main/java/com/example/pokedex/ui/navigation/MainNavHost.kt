package com.example.pokedex.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokedex.ui.screens.HomeScreen
import com.example.pokedex.ui.screens.PokemonDetailsScreen
import com.example.pokedex.ui.screens.PokemonListScreen
import com.example.pokedex.ui.screens.ProfileScreen
import com.example.pokedex.ui.screens.SearchScreen

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    onNavigateToLogin: () -> Unit,
) {
    fun onGoBack() {
        navController.popBackStack()
    }

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
                    onGoBack()
                }
            )
        }
        composable(
            "ProfileScreen"
        ) {
            ProfileScreen(
                onSignOut = { onNavigateToLogin() },
                onGoBack = {
                    onGoBack()
                }
            )
        }
        composable("PokemonListScreen") {
            PokemonListScreen(
                onPokemonNameClick = { pokemonName ->
                    navController.navigate("PokemonDetailsScreen/${pokemonName}")
                },
                onGoBack = {
                    onGoBack()
                }
            )
        }
        composable("HomeScreen") {
            HomeScreen(
                onNavigateToPokemonList = { navController.navigate("PokemonListScreen") },
                onNavigateToSearch = { navController.navigate("SerchScreen") },
                onNavigateToProfile = { navController.navigate("ProfileScreen") },
                onPokemonClick = { pokemonName: String ->
                    navController.navigate("PokemonDetailsScreen/${pokemonName}")
                },
            )
        }
        composable("SerchScreen") {
            SearchScreen(
                onPokemonNameClick = { pokemonName ->
                    navController.navigate("PokemonDetailsScreen/${pokemonName}")
                },
                onGoBack = {
                    onGoBack()
                }
            )
        }
    }
}