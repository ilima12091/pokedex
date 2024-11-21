package com.example.pokedex.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.api.responses.FetchPokemonDetailsResponse
import com.example.pokedex.data.PokemonRepository
import com.example.pokedex.ui.utils.getPokemonIdsForHomeScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val pokemonDetailsList: List<FetchPokemonDetailsResponse> = emptyList()
)

class HomeViewModel: ViewModel() {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    fun clearErrorMessage() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun fetchPokemonDetailsForHome() {
        _uiState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {
            try {
                val pokemonIds = getPokemonIdsForHomeScreen()
                Log.d("fetchPokemonDetailsForHome", "Fetching details for Pokémon IDs: $pokemonIds")

                val pokemonDetailsList = pokemonIds.map { id ->
                    async {
                        PokemonRepository.fetchPokemonDetails(id)
                    }
                }.awaitAll()

                _uiState.update {
                    it.copy(
                        pokemonDetailsList = pokemonDetailsList,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                Log.e("PokemonDetails", "Error fetching data: ${e.message}")
                _uiState.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }

}