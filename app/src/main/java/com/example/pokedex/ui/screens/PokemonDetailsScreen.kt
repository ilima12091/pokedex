package com.example.pokedex.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.pokedex.ui.components.TextChip
import com.example.pokedex.ui.utils.getColorFromType
import com.example.pokedex.ui.viewModels.PokemonDetailsViewModel

@Composable
fun PokemonDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: PokemonDetailsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedTabIndex = uiState.selectedTabIndex

    val tabs = listOf("About", "Base Stats", "Evolution", "Moves")
    val pagerState = rememberPagerState(
        initialPage = uiState.selectedTabIndex,
        pageCount = {
            tabs.size
        }
    )

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            viewModel.setSelectedTabIndex(page)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(getColorFromType("grass"))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Bulbasaur",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                LazyRow {
                    item {
                        TextChip(
                            text = "Grass",
                            color = Color(0x66FFFFFF),
                            modifier = Modifier.width(100.dp)
                        )
                    }
                }
            }
            Text(
                text = "#001",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp
            )
        }
        Box(
            Modifier
                .fillMaxSize()
        ) {
            AsyncImage(
                model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/3.png",
                contentDescription = "Bulbasaur",
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.TopCenter)
                    .zIndex(1f),
                contentScale = ContentScale.FillHeight
            )
            Column(
                modifier = Modifier
                    .padding(top = 140.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 40.dp,
                            topEnd = 40.dp
                        )
                    )
                    .background(color = Color.White)
                    .padding(vertical = 32.dp)
                    .fillMaxSize()
            ) {
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    containerColor = Color.Transparent,
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            text = { Text(title) },
                            selected = index == selectedTabIndex,
                            onClick = { viewModel.setSelectedTabIndex(index) }
                        )
                    }
                }
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    when (selectedTabIndex) {
                        0 -> {
                            PokemonAboutScreen()
                        }

                        1 -> {
                            Text("Base Stats")
                        }

                        2 -> {
                            Text("Evolution")
                        }

                        3 -> {
                            Text("Moves")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonDetailsScreenPreview() {
    PokemonDetailsScreen()
}