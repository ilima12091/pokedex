package com.example.pokedex.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.pokedex.api.responses.FetchPokemonDetailsResponse
import com.example.pokedex.ui.components.DataRow
import com.example.pokedex.ui.utils.capitalizeString
import com.example.pokedex.ui.utils.playSound

@Composable
fun PokemonAboutScreen(
    pokemonDetails: FetchPokemonDetailsResponse?
) {
    val context = LocalContext.current

    Column(
        Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        DataRow("Height:", pokemonDetails?.height?.toString() ?: "Unknown")
        DataRow("Weight:", pokemonDetails?.weight?.toString() ?: "Unknown")
        DataRow(
            "Abilities:",
            pokemonDetails?.abilities?.joinToString(", ") { capitalizeString(it.ability.name) }
                ?: "Unknown"
        )
        Text("Pokemon cries:")
        Row {
            TextButton(onClick = {
                playSound(pokemonDetails?.cries?.latest ?: "", context)
            }) {
                Text("Play latest cry")
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play arrow"
                )
            }
            TextButton(onClick = {
                playSound(pokemonDetails?.cries?.legacy ?: "", context)
            }) {
                Text("Play legacy cry")
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play arrow"
                )
            }
        }
    }
}