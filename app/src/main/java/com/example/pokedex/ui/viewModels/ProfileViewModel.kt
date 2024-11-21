package com.example.pokedex.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ProfileUiState(
    val isLoading: Boolean = false,
    val name: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val errorMessage: String? = null,
)

class ProfileViewModel: ViewModel() {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    fun signOut() {
        firebaseAuth.signOut()
    }

    fun clearErrorMessage() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun fetchUserDetails() {
        val uid = firebaseAuth.currentUser?.uid
        val email = firebaseAuth.currentUser?.email

        if (uid == null || email == null) {
            _uiState.value = ProfileUiState(
                isLoading = false,
                errorMessage = "User is not logged in."
            )
            return
        }

        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        firestore.collection("users").document(uid)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val name = document.getString("name")
                    val lastName = document.getString("lastName")
                    _uiState.value = ProfileUiState(
                        isLoading = false,
                        name = name,
                        lastName = lastName,
                        email = email,
                        errorMessage = null
                    )
                } else {
                    _uiState.value = ProfileUiState(
                        isLoading = false,
                        errorMessage = "User details not found."
                    )
                }
            }
            .addOnFailureListener { exception ->
                _uiState.value = ProfileUiState(
                    isLoading = false,
                    errorMessage = "Failed to fetch user details: ${exception.message}"
                )
            }
    }
}