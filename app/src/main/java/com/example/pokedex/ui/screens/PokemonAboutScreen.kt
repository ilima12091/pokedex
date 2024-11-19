package com.example.pokedex.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokedex.api.responses.FetchPokemonDetailsResponse
import com.example.pokedex.ui.components.PokemonDataRow
import com.example.pokedex.ui.utils.capitalizeString

@Composable
fun PokemonAboutScreen(
    pokemonDetails: FetchPokemonDetailsResponse?
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PokemonDataRow("Height:", pokemonDetails?.height?.toString() ?: "Unknown")
        PokemonDataRow("Weight:", pokemonDetails?.weight?.toString() ?: "Unknown")
        PokemonDataRow(
            "Abilities:",
            pokemonDetails?.abilities?.joinToString(", ") { capitalizeString(it.ability.name) }
                ?: "Unknown"
        )
    }
}