package com.example.pokedex.ui.components.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokedex.ui.utils.ValidationResult
import com.example.pokedex.ui.utils.Validator

@Composable
fun CreateUserForm(
    email: String,
    setEmail: (String) -> Unit,
    password: String,
    setPassword: (String) -> Unit,
    confirmPassword: String,
    setConfirmPassword: (String) -> Unit,
    name: String,
    setName: (String) -> Unit,
    lastName: String,
    setLastName: (String) -> Unit,
    onCreateUserClick: () -> Unit,
) {
    val (emailError, setEmailError) = remember { mutableStateOf<String?>(null) }
    val (passwordError, setPasswordError) = remember { mutableStateOf<String?>(null) }
    val (confirmPasswordError, setConfirmPasswordError) = remember { mutableStateOf<String?>(null) }
    val (hasAttemptedSubmit, setHasAttemptedSubmit) = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PlainTextField(
            value = name,
            onValueChange = setName,
            placeholderText = "First Name",
        )
        Spacer(modifier = Modifier.height(16.dp))
        PlainTextField(
            value = lastName,
            onValueChange = setLastName,
            placeholderText = "Last Name",
        )
        Spacer(modifier = Modifier.height(16.dp))
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
                            when (val result = Validator.validatePassword(it)) {
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
        ValidatedField(
            field = {
                PasswordField(
                    password = confirmPassword,
                    setPassword = {
                        setConfirmPassword(it)
                        if (hasAttemptedSubmit) {
                            when (val result = Validator.validateConfirmPassword(password, it)) {
                                is ValidationResult.Success -> setConfirmPasswordError(null)
                                is ValidationResult.Error -> setConfirmPasswordError(result.message)
                            }
                        }
                    },
                    placeholderText = "Confirm Password",
                )
            },
            errorMessage = if (hasAttemptedSubmit) confirmPasswordError else null
        )
        Spacer(modifier = Modifier.height(16.dp))
        PasswordRequirements(
            show = hasAttemptedSubmit && passwordError != null,
            password = password
        )
        Spacer(modifier = Modifier.height(16.dp))
        val isFormValid = emailError == null && passwordError == null && confirmPasswordError == null
        SubmitButton(
            text = "Create User",
            enabled = isFormValid,
            onClick = {
                setHasAttemptedSubmit(true)

                when (val result = Validator.validateEmail(email)) {
                    is ValidationResult.Success -> setEmailError(null)
                    is ValidationResult.Error -> setEmailError(result.message)
                }

                when (val result = Validator.validatePassword(password)) {
                    is ValidationResult.Success -> setPasswordError(null)
                    is ValidationResult.Error -> setPasswordError(result.message)
                }

                when (val result = Validator.validateConfirmPassword(password, confirmPassword)) {
                    is ValidationResult.Success -> setConfirmPasswordError(null)
                    is ValidationResult.Error -> setConfirmPasswordError(result.message)
                }

                if (emailError != null && passwordError != null && confirmPasswordError != null) {
                    onCreateUserClick()
                }
            }
        )
    }
}