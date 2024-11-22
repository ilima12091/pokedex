package com.example.pokedex.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.FirebaseAuthRepository
import com.example.pokedex.data.FirestoreUserRepository
import com.example.pokedex.data.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CreateUserViewModel : ViewModel() {
    private val firebaseAuthRepository = FirebaseAuthRepository()
    private val firestoreUserRepository = FirestoreUserRepository()
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

        viewModelScope.launch {
            firebaseAuthRepository.register(email, password).fold(
                onSuccess = { user ->
                    val uid = user?.uid
                    if (uid != null) {
                        val userDetails = User(
                            name = name,
                            lastName = lastName,
                            email = email,
                            favorites = emptyList()
                        )
                        firestoreUserRepository.saveUser(uid, userDetails).fold(
                            onSuccess = {
                                loginAfterCreation(email, password)
                            },
                            onFailure = { exception ->
                                _createUserState.value = CreateUserState.Error(exception.message ?: "Failed to save user details")
                            }
                        )
                    } else {
                        _createUserState.value = CreateUserState.Error("Failed to retrieve user ID")
                    }
                },
                onFailure = { exception ->
                    _createUserState.value = CreateUserState.Error(exception.message ?: "Failed to create account")
                }
            )
        }
    }

    private fun loginAfterCreation(email: String, password: String) {
        viewModelScope.launch {
            firebaseAuthRepository.login(email, password).fold(
                onSuccess = {
                    _createUserState.value = CreateUserState.Success
                },
                onFailure = { exception ->
                    _createUserState.value = CreateUserState.Error(exception.message ?: "Failed to log in after account creation")
                }
            )
        }
    }
}

sealed class CreateUserState {
    object Idle : CreateUserState()
    object Loading : CreateUserState()
    object Success : CreateUserState()
    data class Error(val message: String) : CreateUserState()
}