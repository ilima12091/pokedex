package com.example.pokedex.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokedex.ui.components.pokemonList.PokedexHeader
import com.example.pokedex.ui.components.pokemonList.PokemonRow
import com.example.pokedex.ui.viewModels.PokemonViewModel

@Composable
fun PokemonListScreen(
    modifier: Modifier = Modifier,
    viewModel: PokemonViewModel = viewModel(),
    onPokemonNameClick: (String) -> Unit,
    onGoBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    val isLoading = uiState.isLoading
    val pokemons = uiState.pokemonList

    val listState = rememberLazyListState()
    val buffer = 4

    val reachedBottom: Boolean by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            val lastVisibleIndex = lastVisibleItem?.index
            lastVisibleIndex != null && lastVisibleIndex >= listState.layoutInfo.totalItemsCount - buffer
        }
    }

    LaunchedEffect(reachedBottom) {
        if (reachedBottom) viewModel.fetchPokemons()
    }

    Scaffold { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            PokedexHeader(
                onGoBack = {
                    onGoBack()
                }
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
            ) {
                if (pokemons.isNotEmpty()) {
                    LazyColumn(
                        state = listState,
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(pokemons) { pokemon ->
                            PokemonRow(
                                pokemon = pokemon,
                                onClick = { onPokemonNameClick(pokemon.name) }
                            )
                        }
                    }
                }

                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}
