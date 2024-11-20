package com.example.pokedex.ui.screens.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokedex.ui.components.auth.LoginFooter
import com.example.pokedex.ui.components.auth.LoginForm
import com.example.pokedex.ui.components.auth.LoginHeader

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
        modifier=modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LoginHeader()
        LoginForm(
            email=email,
            setEmail=setEmail,
            password=password,
            setPassword=setPassword,
            onLoginClick= { viewModel.login(email, password) },
        )
        Spacer(modifier = Modifier.height(16.dp))
        LoginFooter(onNavigateToCreateUser = onNavigateToCreateUser)
    }

    if (loginState is LoginState.Loading) {
//        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        Text(text="Loading...")
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
        else -> {

        }
    }

    SnackbarHost(hostState = snackbarHostState)
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview(modifier: Modifier = Modifier) {
    PokedexTheme {
        LoginScreen(modifier=modifier, onLoginSuccess = {}, onNavigateToCreateUser = {})
    }
}