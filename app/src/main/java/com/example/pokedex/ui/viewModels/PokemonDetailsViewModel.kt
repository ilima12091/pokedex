package com.example.pokedex.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.api.mappers.toTypeNames
import com.example.pokedex.api.responses.FetchPokemonDetailsResponse
import com.example.pokedex.api.responses.PokemonType
import com.example.pokedex.api.responses.Species
import com.example.pokedex.data.FirebaseAuthRepository
import com.example.pokedex.data.FirestoreUserRepository
import com.example.pokedex.data.PokemonRepository
import com.example.pokedex.data.models.FavoritePokemon
import com.example.pokedex.ui.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PokemonDetailsScreenUiState(
    val pokemonDetails: FetchPokemonDetailsResponse? = null,
    val isLoading: Boolean = false,
    val evolutionChain: List<Species>? = null,
    val isLoadingEvolutionChain: Boolean = false,
    val isFavorite: Boolean = false,
    val errorMessage: String? = null,
    val profilePictureUrl: String? = null
)

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val firestoreUserRepository: FirestoreUserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(PokemonDetailsScreenUiState())
    val uiState = _uiState.asStateFlow()

    fun setProfilePicture(imageUrl: String) {
        viewModelScope.launch {
            try {
                val currentUser = firebaseAuthRepository.getCurrentUserOrThrow()

                firestoreUserRepository.updateUserProfilePicture(currentUser.uid, imageUrl)

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    profilePictureUrl = imageUrl,
                    errorMessage = null
                )
            } catch (e: IllegalStateException) {
                Log.e("SetProfilePicture", "User is not logged in: ${e.message}", e)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            } catch (e: Exception) {
                Log.e("SetProfilePicture", "Failed to set profile picture: ${e.message}", e)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = Constants.ErrorMessages.FAILED_TO_PERFORM_ACTION
                )
            }
        }
    }

    fun toggleFavorite(name: String, id: String, sprite: String, types: List<PokemonType>) {
        viewModelScope.launch {
            try {
                val currentUser = firebaseAuthRepository.getCurrentUserOrThrow()
                val uid = currentUser.uid

                firestoreUserRepository.getFavorites(uid).fold(
                    onSuccess = { currentFavorites ->
                        val mutableFavorites = currentFavorites.toMutableList()

                        if (_uiState.value.isFavorite) {
                            mutableFavorites.removeIf { it.name == name }
                        } else {
                            val typeNames = types.toTypeNames()
                            val newFavorite = FavoritePokemon(
                                name = name,
                                id = id,
                                sprite = sprite,
                                types = typeNames,
                            )
                            mutableFavorites.add(newFavorite)
                        }

                        firestoreUserRepository.updateFavorites(uid, mutableFavorites).fold(
                            onSuccess = {
                                _uiState.value = _uiState.value.copy(
                                    isFavorite = !_uiState.value.isFavorite
                                )
                            },
                            onFailure = { updateError ->
                                Log.e("ToggleFavorite", "Failed to update favorites: ${updateError.message}", updateError)
                                _uiState.value = _uiState.value.copy(
                                    errorMessage = Constants.ErrorMessages.FAILED_TO_PERFORM_ACTION
                                )
                            }
                        )
                    },
                    onFailure = { fetchError ->
                        Log.e("ToggleFavorite", "Failed to fetch favorites: ${fetchError.message}", fetchError)
                        _uiState.value = _uiState.value.copy(
                            errorMessage = Constants.ErrorMessages.FAILED_TO_FETCH_FAVORITES,
                        )
                    }
                )
            } catch (e: IllegalStateException) {
                Log.e("ToggleFavorite", "User is not logged in: ${e.message}", e)
                _uiState.value = _uiState.value.copy(
                    errorMessage = e.message
                )
            } catch (e: Exception) {
                Log.e("ToggleFavorite", "An unexpected error occurred: ${e.message}", e)
                _uiState.value = _uiState.value.copy(
                    errorMessage = Constants.ErrorMessages.FAILED_TO_PERFORM_ACTION
                )
            }
        }
    }

    fun fetchPokemonDetails(name: String) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                val currentUser = firebaseAuthRepository.getCurrentUserOrThrow()
                val uid = currentUser.uid

                Log.d("fetchPokemonDetails", "Fetching details for Pokémon: $name")

                val pokemonDetails = PokemonRepository.fetchPokemonDetails(name)

                // Fetch favorites
                val isFavorite = firestoreUserRepository.getFavorites(uid).fold(
                    onSuccess = { favorites ->
                        favorites.any { it.name == pokemonDetails.name }
                    },
                    onFailure = {
                        Log.e("PokemonDetails", "Failed to fetch favorites: ${it.message}", it)
                        false
                    }
                )

                // Fetch profile picture URL
                val profilePictureUrl = firestoreUserRepository.getProfilePictureUrl(uid).fold(
                    onSuccess = { url -> url },
                    onFailure = {
                        Log.e("PokemonDetails", "Failed to fetch profile picture URL: ${it.message}", it)
                        null
                    }
                )

                _uiState.update {
                    it.copy(
                        pokemonDetails = pokemonDetails,
                        isFavorite = isFavorite,
                        profilePictureUrl = profilePictureUrl,
                        isLoading = false
                    )
                }
            } catch (e: IllegalStateException) {
                Log.e("fetchPokemonDetails", "User is not logged in: ${e.message}", e)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message
                    )
                }
            } catch (e: Exception) {
                Log.e("fetchPokemonDetails", "Unexpected error: ${e.message}", e)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = Constants.ErrorMessages.FAILED_TO_FETCH_DATA
                    )
                }
            }
        }
    }

    fun fetchPokemonEvolutionChain(name: String) {
        _uiState.update {
            it.copy(isLoadingEvolutionChain = true)
        }
        viewModelScope.launch {
            val evolutionChain = PokemonRepository.fetchPokemonEvolutionChain(name)
            _uiState.update {
                it.copy(
                    evolutionChain = evolutionChain,
                    isLoadingEvolutionChain = false
                )
            }
        }
    }

    fun clearErrorMessage() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}