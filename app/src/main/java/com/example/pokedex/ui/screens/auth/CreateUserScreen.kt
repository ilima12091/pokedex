package com.example.pokedex.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokedex.ui.components.auth.AuthHeader
import com.example.pokedex.ui.components.auth.CreateUserForm
import com.example.pokedex.ui.theme.SnackbarActionColor
import com.example.pokedex.ui.theme.SnackbarContainerColor
import com.example.pokedex.ui.viewModels.CreateUserState
import com.example.pokedex.ui.viewModels.CreateUserViewModel


@Composable
fun CreateUserScreen(
    modifier: Modifier = Modifier,
    onUserCreated: () -> Unit,
    onBackToLogin: () -> Unit,
    viewModel: CreateUserViewModel = hiltViewModel(),
) {
    val (email, setEmail) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }
    val (confirmPassword, setConfirmPassword) = remember { mutableStateOf("") }
    val (name, setName) = remember { mutableStateOf("") }
    val (lastName, setLastName) = remember { mutableStateOf("") }
    val createUserState by viewModel.createUserState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            IconButton(onClick = onBackToLogin) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Go back",
                    Modifier.size(28.dp),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                AuthHeader(
                    title = "Register Your Account",
                    subtitle = "Become a Pokémon Master",
                )
                CreateUserForm(
                    email = email,
                    setEmail = setEmail,
                    password = password,
                    setPassword = setPassword,
                    confirmPassword = confirmPassword,
                    setConfirmPassword = setConfirmPassword,
                    name = name,
                    setName = setName,
                    lastName = lastName,
                    setLastName = setLastName,
                    isLoading = createUserState is CreateUserState.Loading,
                    onCreateUserClick = {
                        viewModel.createUser(
                            email,
                            password,
                            confirmPassword,
                            name,
                            lastName,
                        )
                    },
                )

                when (createUserState) {
                    is CreateUserState.Success -> {
                        LaunchedEffect(Unit) {
                            onUserCreated()
                        }
                    }

                    is CreateUserState.Error -> {
                        val errorMessage = (createUserState as CreateUserState.Error).message
                        LaunchedEffect(errorMessage) {
                            snackbarHostState.showSnackbar(errorMessage)
                        }
                    }

                    else -> {}
                }
            }
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
}