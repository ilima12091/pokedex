package com.example.pokedex.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import com.example.pokedex.ui.theme.SemiTransparentWhite
import com.example.pokedex.ui.utils.capitalizeString
import com.example.pokedex.ui.utils.getColorFromType

@Composable
fun PokemonCard(
    name: String,
    imageUrl: String,
    types: List<String>,
    onPokemonClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(getColorFromType(types.first()))
            .clickable { onPokemonClick() }
            .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
            .height(140.dp)
    ) {
        Column(
            Modifier.zIndex(1f)
        ) {
            Text(
                text = capitalizeString(name),
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(types) { type ->
                    TextChip(
                        text = capitalizeString(type),
                        color = SemiTransparentWhite,
                        fontSize = 14.sp,
                        verticalPadding = 2.dp,
                        horizontalPadding = 8.dp
                    )
                }
            }
        }
        AsyncImage(
            model = imageUrl,
            contentDescription = "Pokemon Image",
            modifier = Modifier
                .height(100.dp)
                .align(Alignment.BottomEnd),
            contentScale = ContentScale.Fit,
        )
    }
}
