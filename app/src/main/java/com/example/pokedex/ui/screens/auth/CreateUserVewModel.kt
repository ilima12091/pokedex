package com.example.pokedex.ui.screens.auth

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CreateUserViewModel : ViewModel() {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _createUserState = MutableStateFlow<CreateUserState>(CreateUserState.Idle)
    val createUserState: StateFlow<CreateUserState> = _createUserState

    fun createUser(email: String, password: String, confirmPassword: String) {
        _createUserState.value = CreateUserState.Loading

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _createUserState.value = CreateUserState.Success
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