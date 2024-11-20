package com.example.pokedex.ui.components.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokedex.ui.utils.ValidationResult
import com.example.pokedex.ui.utils.Validator

@Composable
fun LoginForm(
    email: String,
    setEmail: (String) -> Unit,
    password: String,
    setPassword: (String) -> Unit,
    onLoginClick: () -> Unit,
    isLoading: Boolean,
) {
    val (emailError, setEmailError) = remember { mutableStateOf<String?>(null) }
    val (passwordError, setPasswordError) = remember { mutableStateOf<String?>(null) }
    val (hasAttemptedSubmit, setHasAttemptedSubmit) = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ValidatedField(
            field = {
                EmailField(
                    email = email,
                    setEmail = {
                        setEmail(it)
                        if (hasAttemptedSubmit) {
                            when (val result = Validator.validateEmail(it)) {
                                is ValidationResult.Success -> setEmailError(null)
                                is ValidationResult.Error -> setEmailError(result.message)
                            }
                        }
                    }
                )
            },
            errorMessage = if (hasAttemptedSubmit) emailError else null
        )
        Spacer(modifier = Modifier.height(16.dp))
        ValidatedField(
            field = {
                PasswordField(
                    password = password,
                    setPassword = {
                        setPassword(it)
                        if (hasAttemptedSubmit) {
                            when (val result = Validator.validateNonEmpty(it, "Password")) {
                                is ValidationResult.Success -> setPasswordError(null)
                                is ValidationResult.Error -> setPasswordError(result.message)
                            }
                        }
                    }
                )
            },
            errorMessage = if (hasAttemptedSubmit) passwordError else null
        )
        Spacer(modifier = Modifier.height(16.dp))

        val noFormErrors = emailError == null && passwordError == null
        val isFormValid = email.isNotBlank() && password.isNotBlank() && noFormErrors
        SubmitButton(
            text = "Sign In",
            enabled = noFormErrors && !isLoading,
            showLoader = isLoading,
            onClick = {
                setHasAttemptedSubmit(true)

                when (val result = Validator.validateEmail(email)) {
                    is ValidationResult.Success -> setEmailError(null)
                    is ValidationResult.Error -> setEmailError(result.message)
                }

                when (val result = Validator.validateNonEmpty(password, "Password")) {
                    is ValidationResult.Success -> setPasswordError(null)
                    is ValidationResult.Error -> setPasswordError(result.message)
                }

                if (isFormValid) {
                    onLoginClick()
                }
            }
        )
    }
}