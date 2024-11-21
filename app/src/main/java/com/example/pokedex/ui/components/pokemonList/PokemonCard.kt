package com.example.pokedex.ui.components.pokemonList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokedex.R
import com.example.pokedex.api.responses.PokemonListItem
import com.example.pokedex.ui.utils.capitalizeString
import com.example.pokedex.ui.utils.getPokemonOrder
import com.example.pokedex.ui.utils.getPokemonOrderFromUrl

@Composable
fun PokemonCard(
    pokemon: PokemonListItem?,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.pokeball_icon),
                contentDescription = "Pokemon Image",
                modifier = Modifier.size(40.dp)
            )
            Text(
                text = capitalizeString(pokemon?.name ?: ""),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
        Text(
            text = getPokemonOrder(getPokemonOrderFromUrl(pokemon?.url ?: "")),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

@Preview
@Composable
fun PokemonCardPreview() {
    PokemonCard(
        pokemon = PokemonListItem(
            name = "bulbasaur",
            url = "https://pokeapi.co/api/v2/pokemon/1/"
        ),
        onClick = {}
    )
}