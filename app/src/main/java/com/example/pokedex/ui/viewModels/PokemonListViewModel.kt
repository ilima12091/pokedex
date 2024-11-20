package com.example.pokedex.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.api.responses.PokemonListItem
import com.example.pokedex.data.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PokemonViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(PokemonScreenUiState())
    val uiState = _uiState.asStateFlow()


    private var currentOffset = 0
    private val pageSize = 20

    init {
        fetchPokemons()
    }

    internal fun fetchPokemons() {
        viewModelScope.launch {
            val newPokemons = PokemonRepository.fetchPokemons(offset = currentOffset)
            _uiState.update { currentState ->
                currentState.copy(
                    pokemonList = currentState.pokemonList + newPokemons,
                    isLoading = false
                )
            }
            currentOffset += pageSize
        }
    }
}

data class PokemonScreenUiState(
    val pokemonList: List<PokemonListItem> = emptyList(),
    val isLoading: Boolean = true,
)