package com.example.pokedex.ui.components.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginForm(
    email: String,
    setEmail: (String) -> Unit,
    password: String,
    setPassword: (String) -> Unit,
    onLoginClick: () -> Unit,
) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        EmailField(email=email, setEmail=setEmail)
        Spacer(modifier = Modifier.height(16.dp))
        PasswordField(password=password, setPassword=setPassword)
        Spacer(modifier = Modifier.height(16.dp))
        SubmitButton(text="Sign In", onClick = onLoginClick)
    }

}