package com.example.pokedex.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.FirebaseAuthRepository
import com.example.pokedex.data.FirestoreUserRepository
import com.example.pokedex.data.models.User
import com.example.pokedex.ui.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateUserViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val firestoreUserRepository: FirestoreUserRepository
) : ViewModel() {
    private val _createUserState = MutableStateFlow<CreateUserState>(CreateUserState.Idle)
    val createUserState: StateFlow<CreateUserState> = _createUserState

    fun createUser(email: String, password: String, confirmPassword: String, name: String, lastName: String) {
        if (email.isBlank() || password.isBlank()) {
            _createUserState.value = CreateUserState.Error(
                Constants.ErrorMessages.EMAIL_AND_PASS_CANNOT_BE_EMPTY
            )
            return
        }

        if (password != confirmPassword) {
            _createUserState.value = CreateUserState.Error(
                Constants.ErrorMessages.PASS_DO_NOT_MATCH
            )
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
                                Log.e("CreateUserViewModel", "Failed to save user details: ${exception.message}", exception)
                                _createUserState.value = CreateUserState.Error(
                                    exception.message ?: Constants.ErrorMessages.FAILED_TO_SAVE_USER_DETAILS
                                )
                            }
                        )
                    } else {
                        Log.e("CreateUserViewModel", "Failed to retrieve user ID")
                        _createUserState.value = CreateUserState.Error(
                            Constants.ErrorMessages.FAILED_TO_RETRIEVE_USER_ID
                        )
                    }
                },
                onFailure = { exception ->
                    Log.e("CreateUserViewModel", "Failed to create account: ${exception.message}", exception)
                    _createUserState.value =
                        CreateUserState.Error(
                            exception.message ?: Constants.ErrorMessages.FAILED_TO_CREATE_ACCOUNT
                        )
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
                    Log.e("CreateUserViewModel", "Failed to login after account creation: ${exception.message}", exception)
                    _createUserState.value = CreateUserState.Error(
                        Constants.ErrorMessages.FAILED_TO_LOGIN
                    )
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