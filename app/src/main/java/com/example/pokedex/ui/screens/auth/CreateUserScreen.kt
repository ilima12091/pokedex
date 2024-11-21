package com.example.pokedex.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CreateUserScreen(
    modifier: Modifier = Modifier,
    onUserCreated: () -> Unit,
    onBackToLogin: () -> Unit,
    viewModel: CreateUserViewModel = viewModel()
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFFEEEEEE)),
            ) {
                IconButton(onClick = onBackToLogin) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Go back",
                        Modifier.size(28.dp),
                        colorResource(id = android.R.color.black)
                    )
                }
                Text(
                    text = "Register Your Account",
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier=modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = Color(0xFFEEEEEE)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CreateUserForm(
                email=email,
                setEmail=setEmail,
                password=password,
                setPassword=setPassword,
                confirmPassword = confirmPassword,
                setConfirmPassword=setConfirmPassword,
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

        SnackbarHost(
            hostState = snackbarHostState,
        ) { data ->
            Snackbar(
                snackbarData = data,
                containerColor = Color(0xFFFFCDD2),
                contentColor = Color.Black,
                actionColor = Color(0xFFD32F2F),
            )
        }
    }
}