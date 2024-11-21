package com.example.pokedex.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.pokedex.api.mappers.toTypeNames
import com.example.pokedex.ui.viewModels.HomeUiState
import com.example.pokedex.ui.viewModels.HomeViewModel
import com.example.pokedex.ui.viewModels.ProfileViewModel
import com.google.android.material.progressindicator.CircularProgressIndicator

@Composable
fun HomeTopBar(
    onNavigateToPokemonList: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(WindowInsets.statusBars.asPaddingValues())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {}) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Go back",
                Modifier.size(28.dp),
                colorResource(id = android.R.color.white)
            )
        }
        IconButton(onClick = {}) {
            Icon(
                Icons.Outlined.AccountCircle,
                contentDescription = "AccountDetails",
                Modifier.size(28.dp),
                colorResource(id = android.R.color.white)
            )
        }
        TextButton(
            onClick = {
                onNavigateToPokemonList()
            },
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("See all pokemon", fontSize = 18.sp, color = Color.Red)
            }
        }
    }
}

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
                    PokemonItem(
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

@Composable
fun PokemonItem(
    name: String,
    imageUrl: String,
    types: List<String>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "Pokemon Image",
            modifier = Modifier
                .size(64.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = name.capitalize()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = types.joinToString(", ") { it.capitalize() }
            )
        }
    }
}




