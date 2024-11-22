package com.example.pokedex.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.FirebaseAuthRepository
import com.example.pokedex.data.FirestoreUserRepository
import com.example.pokedex.ui.utils.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ProfileUiState(
    val isLoading: Boolean = false,
    val name: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val errorMessage: String? = null,
    val profilePictureUrl: String? = null
)

class ProfileViewModel : ViewModel() {
    private val firebaseAuthRepository = FirebaseAuthRepository()
    private val firestoreUserRepository = FirestoreUserRepository()
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    fun signOut() {
        firebaseAuthRepository.signOut()
    }

    fun clearErrorMessage() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun fetchUserDetails() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            try {
                val currentUser = firebaseAuthRepository.getCurrentUserOrThrow()
                val uid = currentUser.uid
                val email = currentUser.email

                val result = firestoreUserRepository.getUser(uid)
                result.fold(
                    onSuccess = { user ->
                        _uiState.value = ProfileUiState(
                            isLoading = false,
                            name = user.name,
                            lastName = user.lastName,
                            email = email,
                            profilePictureUrl = user.profilePictureUrl,
                            errorMessage = null
                        )
                    },
                    onFailure = { exception ->
                        Log.e("ProfileViewModel", "Failed to fetch user details: ${exception.message}", exception)
                        _uiState.value = ProfileUiState(
                            isLoading = false,
                            errorMessage = Constants.ErrorMessages.FAILED_TO_FETCH_DATA
                        )
                    }
                )
            } catch (e: IllegalStateException) {
                Log.e("ProfileViewModel", "User is not logged in: ${e.message}", e)
                _uiState.value = ProfileUiState(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }
}