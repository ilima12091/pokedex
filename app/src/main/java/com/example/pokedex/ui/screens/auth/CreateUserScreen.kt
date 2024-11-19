package com.example.pokedex.ui.screens.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokedex.ui.components.auth.CreateUserForm
import com.example.pokedex.ui.components.auth.LoginHeader

@Composable
fun CreateUserScreen(
    modifier: Modifier = Modifier,
    onUserCreated: () -> Unit,
    viewModel: CreateUserViewModel = viewModel()
) {
    val (email, setEmail) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }
    val (confirmPassword, setConfirmPassword) = remember { mutableStateOf("") }
    val createUserState by viewModel.createUserState.collectAsState()

    Column(
        modifier=modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LoginHeader()
        CreateUserForm(
            email=email,
            setEmail=setEmail,
            password=password,
            setPassword=setPassword,
            confirmPassword = confirmPassword,
            setConfirmPassword=setConfirmPassword,
            onCreateUserClick = { viewModel.createUser(email, password, confirmPassword) },
        )

        when (createUserState) {
            is CreateUserState.Loading -> {
                CircularProgressIndicator()
            }
            is CreateUserState.Success -> {
                LaunchedEffect(Unit) {
                    onUserCreated()
                }
            }
            is CreateUserState.Error -> {
                val errorMessage = (createUserState as CreateUserState.Error).message
                Text("Error: $errorMessage", color = Color.Red)
            }
            else -> {}
        }
    }
}