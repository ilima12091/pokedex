package com.example.pokedex.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Snackbar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokedex.ui.theme.PokedexTheme

import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokedex.ui.components.auth.LoginFooter
import com.example.pokedex.ui.components.auth.LoginForm
import com.example.pokedex.ui.components.auth.AuthHeader
import com.example.pokedex.ui.theme.AuthBackgroundColor
import com.example.pokedex.ui.theme.SnackbarActionColor
import com.example.pokedex.ui.theme.SnackbarContainerColor
import com.example.pokedex.ui.viewModels.LoginState
import com.example.pokedex.ui.viewModels.LoginViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginSuccess: () -> Unit,
    onNavigateToCreateUser: () -> Unit,
    viewModel: LoginViewModel = viewModel(),
) {
    val (email, setEmail) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }
    val loginState by viewModel.loginState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    Column(
        modifier = modifier
            .background(color = AuthBackgroundColor)
            .fillMaxSize()
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AuthHeader(
            title = "Welcome to your Pokédex!",
            subtitle = "Are you ready to catch'em all?",
        )
        Spacer(modifier = Modifier.height(32.dp))
        LoginForm(
            email=email,
            setEmail=setEmail,
            password=password,
            setPassword=setPassword,
            onLoginClick= { viewModel.login(email, password) },
            isLoading = loginState is LoginState.Loading
        )
        Spacer(modifier = Modifier.height(16.dp))
        LoginFooter(onNavigateToCreateUser = onNavigateToCreateUser)
    }

    when (loginState) {
        is LoginState.Success -> {
            LaunchedEffect(Unit) {
                onLoginSuccess()
            }
        }
        is LoginState.Error -> {
            val errorMessage = (loginState as LoginState.Error).message
            LaunchedEffect(errorMessage) {
                snackbarHostState.showSnackbar(errorMessage)
            }
        }
        else -> { }
    }

    SnackbarHost(
        hostState = snackbarHostState,
    ) { data ->
        Snackbar(
            snackbarData = data,
            containerColor = SnackbarContainerColor,
            contentColor = Color.Black,
            actionColor = SnackbarActionColor,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview(modifier: Modifier = Modifier) {
    PokedexTheme {
        LoginScreen(modifier=modifier, onLoginSuccess = {}, onNavigateToCreateUser = {})
    }
}