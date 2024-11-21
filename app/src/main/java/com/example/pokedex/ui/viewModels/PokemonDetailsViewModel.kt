package com.example.pokedex.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.api.mappers.toTypeNames
import com.example.pokedex.api.responses.FetchPokemonDetailsResponse
import com.example.pokedex.api.responses.PokemonType
import com.example.pokedex.api.responses.Species
import com.example.pokedex.data.PokemonRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class PokemonDetailsScreenUiState(
    val pokemonDetails: FetchPokemonDetailsResponse? = null,
    val isLoading: Boolean = false,
    val evolutionChain: List<Species>? = null,
    val isLoadingEvolutionChain: Boolean = false,
    val isFavorite: Boolean = false,
    val errorMessage: String? = null,
)

class PokemonDetailsViewModel : ViewModel() {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow(PokemonDetailsScreenUiState())
    val uiState = _uiState.asStateFlow()

    fun toggleFavorite(name: String?, sprite: String?, types: List<PokemonType>?) {
        val uid = firebaseAuth.currentUser?.uid ?: return
        firestore.collection("users").document(uid)
            .get()
            .addOnSuccessListener { document ->
                val favorites = (document.get("favorites") as? List<*>)?.mapNotNull { item ->
                    (item as? Map<*, *>)?.filterKeys { it is String }?.mapKeys { it.key as String }
                }?.toMutableList() ?: mutableListOf()
                if (_uiState.value.isFavorite) {
                    favorites.removeIf { it["name"] == name }
                } else {
                    val typeNames = types?.toTypeNames()
                    val newFavorite = mapOf(
                        "name" to name,
                        "types" to typeNames,
                        "sprite" to sprite,
                    )
                    favorites.add(newFavorite)
                }
                firestore.collection("users").document(uid)
                    .update("favorites", favorites)
                    .addOnSuccessListener {
                        _uiState.value = _uiState.value.copy(
                            isFavorite = !_uiState.value.isFavorite,
                        )
                    }
                    .addOnFailureListener { exception ->
                        Log.e("ToggleFavorite", "Failed to fetch favorites: ${exception.message}")
                        _uiState.value = _uiState.value.copy(
                            errorMessage = "Failed to fetch favorites. Please try again."
                        )
                    }
            }
            .addOnFailureListener { exception ->
                Log.e("ToggleFavorite", "Failed to toggle favorites: ${exception.message}")
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Failed to toggle favorites. Please try again."
                )
            }
    }

    fun fetchPokemonDetails(name: String) {
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            try {
                Log.d("fetchPokemonDetails", "Name: $name")
                val pokemonDetails = PokemonRepository.fetchPokemonDetails(name)

                // Fetch favorite status
                val uid = firebaseAuth.currentUser?.uid
                val isFavorite = if (uid != null) {
                    val document = firestore.collection("users").document(uid).get().await()
                    val favorites = (document.get("favorites") as? List<*>)?.mapNotNull { favorite ->
                        (favorite as? Map<*, *>)?.filterKeys { it is String }?.mapKeys { it.key as String }
                    } ?: emptyList()

                    favorites.any { it["name"] == pokemonDetails.name }
                } else {
                    false
                }

                _uiState.update {
                    it.copy(
                        pokemonDetails = pokemonDetails,
                        isFavorite = isFavorite,
                        isLoading = false
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