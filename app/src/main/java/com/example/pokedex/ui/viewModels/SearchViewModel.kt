package com.example.pokedex.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.api.responses.PokemonListItem
import com.example.pokedex.data.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchScreenUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchAllPokemons()
    }

    internal fun fetchAllPokemons() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val allPokemons = pokemonRepository.fetchAllPokemons()
                _uiState.update { currentState ->
                    currentState.copy(
                        pokemonList = allPokemons,
                        pokemonListShow = allPokemons,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
    fun searchPokemon(searchText: String){
        _uiState.update { currentState ->
            currentState.copy(
                searchText = searchText,
                pokemonListShow = if (searchText != "") {
                    currentState.pokemonList.filter { it.name.contains(searchText, ignoreCase = true) }
                }else{
                    currentState.pokemonList
                }
            )
        }
    }
}

data class SearchScreenUiState(
    val pokemonList: List<PokemonListItem> = emptyList(),
    val pokemonListShow: List<PokemonListItem> = emptyList(),
    val isLoading: Boolean = true,
    val searchText: String = ""
)