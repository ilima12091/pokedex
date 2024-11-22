package com.example.pokedex.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokedex.api.mappers.toTypeNames
import com.example.pokedex.ui.components.HomeTopBar
import com.example.pokedex.ui.components.PokemonCard
import com.example.pokedex.ui.viewModels.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    onNavigateToPokemonList: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onPokemonClick: (String) -> Unit,
    onNavigateToSearch: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchPokemonDetailsForHome()
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            HomeTopBar(
                onNavigateToPokemonList = onNavigateToPokemonList,
                onNavigateToProfile = onNavigateToProfile,
                onNavigateToSearch = onNavigateToSearch
            )

            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (uiState.pokemonDetailsList.isNotEmpty()) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(uiState.pokemonDetailsList) { pokemon ->
                        PokemonCard(
                            name = pokemon.name,
                            imageUrl = pokemon.sprites.frontDefault,
                            types = pokemon.types.toTypeNames(),
                            onPokemonClick = { onPokemonClick(pokemon.name) }
                        )
                    }
                }
            } else {
                Text(
                    text = "No Pokémon available",
                )
            }
        }
    }
}