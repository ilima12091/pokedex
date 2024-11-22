package com.example.pokedex.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokedex.ui.theme.SemiTransparentWhite

@Composable
fun HomeTopBar(
    onNavigateToPokemonList: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToSearch: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .defaultMinSize(minHeight = 50.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            onNavigateToProfile()
        }) {
            Icon(
                Icons.Outlined.AccountCircle,
                contentDescription = "AccountDetails",
                Modifier.size(34.dp)
            )
        }
        TextButton(
            onClick = {
                onNavigateToSearch()
            },
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextChip(
                    text = "Search Pokémon",
                    color = SemiTransparentWhite,
                    textColor = Color.Black,
                    modifier = Modifier
                        .defaultMinSize(minWidth = 100.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,

                    )
            }
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
                TextChip(
                    text = "See all Pokemon",
                    color = SemiTransparentWhite,
                    textColor = Color.Black,
                    modifier = Modifier
                        .defaultMinSize(minWidth = 100.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    horizontalPadding = 8.dp,
                    verticalPadding = 8.dp
                )
            }
        }
    }
}