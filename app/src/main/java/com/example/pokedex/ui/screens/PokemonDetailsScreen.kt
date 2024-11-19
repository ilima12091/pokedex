package com.example.pokedex.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.pokedex.R
import com.example.pokedex.ui.components.PokemonDetailsTopBar
import com.example.pokedex.ui.components.TextChip
import com.example.pokedex.ui.utils.capitalizeString
import com.example.pokedex.ui.utils.getColorFromType
import com.example.pokedex.ui.utils.getPokemonOrder
import com.example.pokedex.ui.viewModels.PokemonDetailsViewModel

@Composable
fun PokemonDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: PokemonDetailsViewModel = viewModel(),
    pokemonId: String = "charmeleon"
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedTabIndex = uiState.selectedTabIndex
    val pokemonDetails = uiState.pokemonDetails
    val isLoading = uiState.isLoading
    val evolutionChain = uiState.evolutionChain
    val isLoadingEvolutionChain = uiState.isLoadingEvolutionChain

    val tabs = listOf("About", "Base Stats", "Evolution")
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

    LaunchedEffect(pokemonId) {
        pokemonId.let {
            viewModel.fetchPokemonDetails(it)
        }
    }

    LaunchedEffect(selectedTabIndex) {
        if (selectedTabIndex == 2 && evolutionChain == null) {
            pokemonId.let {
                viewModel.fetchPokemonEvolutionChain(it)
            }
        }
    }

    Scaffold(
        topBar = {
            PokemonDetailsTopBar()
        }
    ) { innerPadding ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = innerPadding.calculateTopPadding()),
                contentAlignment = Alignment.Center
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        } else {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(
                        getColorFromType(
                            pokemonDetails?.types?.getOrNull(0)?.type?.name ?: ""
                        )
                    )
                    .padding(top = innerPadding.calculateTopPadding()),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = capitalizeString(pokemonDetails?.name ?: ""),
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            items(pokemonDetails?.types ?: emptyList()) {
                                TextChip(
                                    text = capitalizeString(it.type.name),
                                    color = Color(0x80FFFFFF),
                                    modifier = Modifier
                                        .defaultMinSize(minWidth = 100.dp),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                    Text(
                        text = getPokemonOrder(pokemonDetails?.order ?: 0),
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
                        model = pokemonDetails?.sprites?.frontDefault ?: "",
                        placeholder = painterResource(R.drawable.pokeball_icon),
                        contentDescription = pokemonDetails?.name ?: "",
                        modifier = Modifier
                            .size(240.dp)
                            .align(Alignment.TopCenter)
                            .zIndex(1f),
                        contentScale = ContentScale.Fit
                    )
                    Column(
                        modifier = Modifier
                            .padding(top = 180.dp)
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
                                    PokemonAboutScreen(pokemonDetails)
                                }

                                1 -> {
                                    PokemonStatsScreen(pokemonDetails)
                                }

                                2 -> {
                                    PokemonEvolutionsScreen(
                                        evolutionChain = evolutionChain,
                                        isLoadingEvolutionChain = isLoadingEvolutionChain,
                                        pokemonDetails = pokemonDetails
                                    )
                                }
                            }
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