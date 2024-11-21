package com.example.pokedex.ui.screens

import androidx.compose.foundation.layout.Column
import com.example.pokedex.ui.components.PokemonCard
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokedex.api.mappers.toTypeNames
import com.example.pokedex.ui.components.HomeTopBar
import com.example.pokedex.ui.viewModels.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    onNavigateToPokemonList: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchPokemonDetailsForHome()
    }

    Column {
        HomeTopBar(
            onNavigateToPokemonList = onNavigateToPokemonList
        )

        if (uiState.isLoading) {
//            CircularProgressIndicator(
//                modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
//            )
        } else if (uiState.pokemonDetailsList.isNotEmpty()) {
            LazyColumn {
                items(uiState.pokemonDetailsList) { pokemon ->
                    PokemonCard(
                        name = pokemon.name,
                        imageUrl = pokemon.sprites.frontDefault,
                        types = pokemon.types.toTypeNames()
                    )
                }
            }
        } else if (uiState.errorMessage != null) {
//            Text(
//                text = uiState.errorMessage
//            )
        } else {
            Text(
                text = "No Pokémon available",
            )
        }
    }
}