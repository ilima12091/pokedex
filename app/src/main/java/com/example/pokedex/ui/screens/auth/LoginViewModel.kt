package com.example.pokedex.ui.screens.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel : ViewModel() {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _loginState.value = LoginState.Error("Email and Password cannot be empty")
            return
        }

        _loginState.value = LoginState.Loading
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _loginState.value = LoginState.Success
                } else {
                    val errorMessage = task.exception?.message ?: "Unknown error"
                    Log.e("LoginViewModel", "Login failed: $errorMessage")

                    _loginState.value = LoginState.Error("Invalid email or password. Please try again.")
                }
            }
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}