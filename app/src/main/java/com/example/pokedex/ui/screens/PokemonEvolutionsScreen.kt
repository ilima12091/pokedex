package com.example.pokedex.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.pokedex.api.responses.FetchPokemonDetailsResponse
import com.example.pokedex.api.responses.Species
import com.example.pokedex.ui.utils.capitalizeString

@Composable
fun PokemonEvolutionsScreen(
    isLoadingEvolutionChain: Boolean,
    evolutionChain: List<Species>?,
    pokemonDetails: FetchPokemonDetailsResponse?,
    onNavigateToPokemon: (String) -> Unit
) {
    if (isLoadingEvolutionChain) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            Modifier
                .fillMaxSize()
                .padding(32.dp)
        ) {
            Text("Evolution Chain", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 32.dp)
            ) {
                itemsIndexed(evolutionChain ?: emptyList()) { index, species ->
                    Column(
                        Modifier
                            .clip(
                                RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                if (species.name != pokemonDetails?.name) {
                                    onNavigateToPokemon(species.name)
                                }
                            }
                            .background(
                                color = if (species.name == pokemonDetails?.name) {
                                    Color.LightGray
                                } else {
                                    Color.Transparent
                                }
                            )
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = species.pokemonDetails.sprites.frontDefault,
                            contentDescription = species.name,
                            modifier = Modifier.size(150.dp)
                        )
                        Text(capitalizeString(species.name))
                    }
                    if (index < (evolutionChain?.size?.minus(1) ?: 0)) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Arrow Forward",
                            tint = Color.Black,
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .size(32.dp)
                        )
                    }
                }
            }
        }
    }
}