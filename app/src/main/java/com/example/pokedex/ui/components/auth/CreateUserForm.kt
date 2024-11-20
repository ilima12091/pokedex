package com.example.pokedex.ui.components.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
        EmailField(email=email, setEmail=setEmail)
        Spacer(modifier = Modifier.height(16.dp))
        PasswordField(password=password, setPassword=setPassword)
        Spacer(modifier = Modifier.height(16.dp))
        PasswordField(
            password=confirmPassword,
            setPassword=setConfirmPassword,
            placeholderText = "Confirm Password"
        )
        Spacer(modifier = Modifier.height(16.dp))
        SubmitButton(text="Create User", onClick = onCreateUserClick)
    }
}