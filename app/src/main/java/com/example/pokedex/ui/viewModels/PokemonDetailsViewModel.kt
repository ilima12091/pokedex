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
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PokemonDetailsScreenUiState(
    val pokemonDetails: FetchPokemonDetailsResponse? = null,
    val isLoading: Boolean = false,
    val evolutionChain: List<Species>? = null,
    val isLoadingEvolutionChain: Boolean = false,
    val isFavorite: Boolean = false,
    val errorMessage: String? = null,
    val profilePictureUrl: String? = null
)

class PokemonDetailsViewModel : ViewModel() {
    private val firebaseAuthRepository = FirebaseAuthRepository()
    private val firestoreUserRepository = FirestoreUserRepository()

    private val _uiState = MutableStateFlow(PokemonDetailsScreenUiState())
    val uiState = _uiState.asStateFlow()

    private fun getCurrentUserOrHandleError(): FirebaseUser? {
        val currentUser = firebaseAuthRepository.getCurrentUser()
        if (currentUser == null) {
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                errorMessage = "User is not logged in."
            )
        }
        return currentUser
    }

    fun setProfilePicture(imageUrl: String) {
        viewModelScope.launch {
            val currentUser = getCurrentUserOrHandleError() ?: return@launch
            try {
                firestoreUserRepository.updateUserProfilePicture(currentUser.uid, imageUrl)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    profilePictureUrl = imageUrl,
                    errorMessage = null
                )
            } catch (e: Exception) {
                Log.e("SetProfilePicture", "Failed to set profile picture: ${e.message}", e)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to set profile picture. Please try again."
                )
            }
        }
    }

    fun toggleFavorite(name: String, id: String, sprite: String, types: List<PokemonType>) {
        viewModelScope.launch {
            val currentUser = getCurrentUserOrHandleError() ?: return@launch
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
                            Log.e("ToggleFavorite", "Failed to update favorites: ${updateError.message}")
                            _uiState.value = _uiState.value.copy(
                                errorMessage = "Failed to update favorites. Please try again."
                            )
                        }
                    )
                },
                onFailure = { fetchError ->
                    Log.e("ToggleFavorite", "Failed to fetch favorites: ${fetchError.message}")
                    _uiState.value = _uiState.value.copy(
                        errorMessage = "Failed to fetch favorites. Please try again."
                    )
                }
            )
        }
    }

    fun fetchPokemonDetails(name: String) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val currentUser = getCurrentUserOrHandleError() ?: return@launch
            try {
                val uid = currentUser.uid
                Log.d("fetchPokemonDetails", "Name: $name")
                val pokemonDetails = PokemonRepository.fetchPokemonDetails(name)

                // Fetch favorites
                val isFavoriteResult = firestoreUserRepository.getFavorites(uid).fold(
                    onSuccess = { favorites ->
                        favorites.any { it.name == pokemonDetails.name }
                    },
                    onFailure = {
                        Log.e("PokemonDetails", "Failed to fetch favorites: ${it.message}")
                        false
                    }
                )

                // Fetch profile picture URL
                val profilePictureResult = firestoreUserRepository.getProfilePictureUrl(uid).fold(
                    onSuccess = { url -> url },
                    onFailure = {
                        Log.e("PokemonDetails", "Failed to fetch profile picture URL: ${it.message}")
                        null
                    }
                )

                _uiState.update {
                    it.copy(
                        pokemonDetails = pokemonDetails,
                        isFavorite = isFavoriteResult,
                        isLoading = false,
                        profilePictureUrl = profilePictureResult
                    )
                }
            } catch (e: Exception) {
                Log.e("PokemonDetails", "Error fetching data: ${e.message}")
                _uiState.update {
                    it.copy(
                        isLoading = false
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