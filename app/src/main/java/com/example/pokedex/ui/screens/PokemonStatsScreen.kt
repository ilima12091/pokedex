package com.example.pokedex.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokedex.api.responses.FetchPokemonDetailsResponse
import com.example.pokedex.ui.components.PokemonStatRow
import com.example.pokedex.ui.utils.getColorFromType

@Composable
fun PokemonStatsScreen(
    pokemonDetails: FetchPokemonDetailsResponse?
) {
    Column(
        Modifier
            .padding(32.dp)
            .fillMaxSize(),
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(pokemonDetails?.stats ?: emptyList()) { stat ->
                PokemonStatRow(
                    stat.stat.name,
                    stat.baseStat,
                    getColorFromType(pokemonDetails?.types?.firstOrNull()?.type?.name ?: "normal")
                )
            }
        }
    }
}