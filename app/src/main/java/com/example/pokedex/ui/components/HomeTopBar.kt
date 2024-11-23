package com.example.pokedex.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

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
        TextButton(
            onClick = {
                onNavigateToPokemonList()
            },
        ) {
            Text("All pokemons", textDecoration = TextDecoration.Underline)
        }
        Row {
            IconButton(onClick = {
                onNavigateToSearch()
            }) {
                Icon(
                    Icons.Outlined.Search,
                    contentDescription = "Search",
                    Modifier.size(32.dp),
                )
            }
            IconButton(onClick = {
                onNavigateToProfile()
            }) {
                Icon(
                    Icons.Outlined.AccountCircle,
                    contentDescription = "AccountDetails",
                    Modifier.size(32.dp)
                )
            }
        }
    }

}