package com.example.pokedex.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.api.responses.FetchPokemonDetailsResponse
import com.example.pokedex.data.FirebaseAuthRepository
import com.example.pokedex.data.FirestoreUserRepository
import com.example.pokedex.data.PokemonRepository
import com.example.pokedex.ui.utils.Constants
import com.example.pokedex.ui.utils.getPokemonIdsForHomeScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val pokemonDetailsList: List<FetchPokemonDetailsResponse> = emptyList()
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val firestoreUserRepository: FirestoreUserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    fun fetchPokemonDetailsForHome() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val currentUser = firebaseAuthRepository.getCurrentUserOrThrow()
                val result = firestoreUserRepository.getFavorites(currentUser.uid)
                val favoriteIds = result.fold(
                    onSuccess = { list -> list.map { it.id } },
                    onFailure = { exception ->
                        Log.e("fetchPokemonDetailsForHome", "Failed to fetch favorites: ${exception.message}", exception)
                        emptyList()
                    }
                )
                Log.d("fetchPokemonDetailsForHome", "Favorite Pokémon ids: $favoriteIds")

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
            } catch (e: IllegalStateException) {
                Log.e("fetchPokemonDetailsForHome", "User is not logged in: ${e.message}", e)
                _uiState.update {
                    it.copy(
                        errorMessage = e.message,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                Log.e("fetchPokemonDetailsForHome", "Error fetching data: ${e.message}", e)
                _uiState.update {
                    it.copy(
                        errorMessage = Constants.ErrorMessages.FAILED_TO_FETCH_DATA,
                        isLoading = false
                    )
                }
            }
        }
    }
}