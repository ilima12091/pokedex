package com.example.pokedex.ui.screens.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.FirebaseAuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel() : ViewModel() {
    private val firebaseAuthRepository = FirebaseAuthRepository()
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _loginState.value = LoginState.Error("Email and Password cannot be empty")
            return
        }

        _loginState.value = LoginState.Loading

        viewModelScope.launch {
            val result = firebaseAuthRepository.login(email, password)
            result.fold(
                onSuccess = { user ->
                    if (user != null) {
                        _loginState.value = LoginState.Success
                    } else {
                        Log.e("LoginViewModel", "Login failed: User is null")
                        _loginState.value = LoginState.Error("Login failed. Please try again.")
                    }
                },
                onFailure = { exception ->
                    Log.e("LoginViewModel", "Login failed: ${exception.message}")
                    _loginState.value = LoginState.Error("Invalid email or password. Please try again.")
                }
            )
        }
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}