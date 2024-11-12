package com.example.pokedex.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.api.responses.FetchPokemonDetailsResponse
import com.example.pokedex.data.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PokemonDetailsScreenUiState(
    val selectedTabIndex: Int = 0,
    val pokemonDetails: FetchPokemonDetailsResponse? = null,
    val isLoading: Boolean = false
)

class PokemonDetailsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PokemonDetailsScreenUiState())
    val uiState = _uiState.asStateFlow()

    fun setSelectedTabIndex(index: Int) {
        _uiState.value = _uiState.value.copy(selectedTabIndex = index)
    }

    fun fetchPokemonDetails(name: String) {
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            val pokemonDetails = PokemonRepository.fetchPokemonDetails(name)
            _uiState.update {
                it.copy(
                    pokemonDetails = pokemonDetails,
                    isLoading = false
                )
            }
        }
    }
}