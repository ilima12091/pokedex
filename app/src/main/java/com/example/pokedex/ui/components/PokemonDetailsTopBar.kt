package com.example.pokedex.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PokemonDetailsTopBar(
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onGoBack: () -> Unit = {},
    onSetAsProfilePictureClick: () -> Unit = {},
    profilePictureUrl: String? = null,
    pokemonImageUrl: String? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(WindowInsets.statusBars.asPaddingValues())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            onGoBack()
        }) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Go back",
                Modifier.size(28.dp),
                colorResource(id = android.R.color.white)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (profilePictureUrl != pokemonImageUrl) {
                TextButton(
                    onClick = {
                        onSetAsProfilePictureClick()
                    },
                ) {
                    Text(
                        "Set as profile picture",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                }
            }

            IconButton(onClick = { onFavoriteClick() }) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite",
                    Modifier.size(28.dp),
                    colorResource(id = android.R.color.white)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonDetailsTopBarPreview() {
    PokemonDetailsTopBar(isFavorite = false, onGoBack = {}, onFavoriteClick = {})
}