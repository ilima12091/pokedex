package com.example.pokedex.ui.screens.auth

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CreateUserViewModel : ViewModel() {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _createUserState = MutableStateFlow<CreateUserState>(CreateUserState.Idle)
    val createUserState: StateFlow<CreateUserState> = _createUserState

    fun createUser(email: String, password: String, confirmPassword: String, name: String, lastName: String) {
        if (email.isBlank() || password.isBlank()) {
            _createUserState.value = CreateUserState.Error("Email and Password cannot be empty")
            return
        }

        if (password != confirmPassword) {
            _createUserState.value = CreateUserState.Error("Passwords do not match.")
            return
        }

        _createUserState.value = CreateUserState.Loading

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = firebaseAuth.currentUser?.uid
                    val userDetails = mapOf(
                        "name" to name,
                        "lastName" to lastName,
                        "email" to email
                    )
                    uid?.let {
                        firestore.collection("users").document(it).set(userDetails)
                            .addOnSuccessListener {
                                _createUserState.value = CreateUserState.Success
                            }
                            .addOnFailureListener { e ->
                                _createUserState.value = CreateUserState.Error(e.message ?: "Failed to save user details")
                            }
                    }
                } else {
                    val errorMessage = task.exception?.message ?: "Unknown error occurred"
                    _createUserState.value = CreateUserState.Error(errorMessage)
                }
            }
    }
}

sealed class CreateUserState {
    object Idle : CreateUserState()
    object Loading : CreateUserState()
    object Success : CreateUserState()
    data class Error(val message: String) : CreateUserState()
}