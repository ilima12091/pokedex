package com.example.pokedex.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.api.responses.FetchPokemonDetailsResponse
import com.example.pokedex.data.FirebaseAuthRepository
import com.example.pokedex.data.FirestoreUserRepository
import com.example.pokedex.data.PokemonRepository
import com.example.pokedex.ui.utils.getPokemonIdsForHomeScreen
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
    private val firebaseAuthRepository = FirebaseAuthRepository()
    private val firestoreUserRepository = FirestoreUserRepository()
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    fun fetchPokemonDetailsForHome() {
        _uiState.update { it.copy(isLoading = true) }

        val currentUser = firebaseAuthRepository.getCurrentUser()
        if (currentUser == null) {
            Log.e("fetchPokemonDetailsForHome", "No user logged in")
            _uiState.update {
                it.copy(
                    errorMessage = "No user logged in",
                    isLoading = false
                )
            }
            return
        }

        viewModelScope.launch {
            try {
                val result = firestoreUserRepository.getFavorites(currentUser.uid)
                val favoriteIds = result.fold(
                    onSuccess = { list -> list.map { it.id } },
                    onFailure = { emptyList() }
                )
                Log.d("fetchPokemonDetailsForHome", "Favorite Pokémon ids: $favoriteIds ")

                val pokemonIds = getPokemonIdsForHomeScreen(favoriteIds = favoriteIds)
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
                Log.e("fetchPokemonDetailsForHome", "Error fetching data: ${e.message}")
                _uiState.update {
                    it.copy(
                        errorMessage = e.message ?: "Failed to fetch data",
                        isLoading = false
                    )
                }
            }
        }
    }
}