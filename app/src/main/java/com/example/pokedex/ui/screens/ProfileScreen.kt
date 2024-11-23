package com.example.pokedex.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.pokedex.R
import com.example.pokedex.ui.components.DataRow
import com.example.pokedex.ui.components.profile.ProfileHeader
import com.example.pokedex.ui.theme.LogoutColor
import com.example.pokedex.ui.theme.SnackbarActionColor
import com.example.pokedex.ui.theme.SnackbarContainerColor
import com.example.pokedex.ui.utils.Constants
import com.example.pokedex.ui.viewModels.ProfileViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onSignOut: () -> Unit,
    onGoBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.fetchUserDetails()
    }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.clearErrorMessage()
        }
    }

    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(Constants.UiConstants.LOADING_INDICATOR_SIZE.dp),
                    strokeWidth = 4.dp
                )
            }
        }

        else -> {
            Scaffold { innerPadding ->
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(top = innerPadding.calculateTopPadding())
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ProfileHeader(
                        onGoBack = onGoBack
                    )
                    Column(
                        modifier = Modifier
                            .padding(vertical = 64.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = uiState.profilePictureUrl ?: "",
                            contentDescription = "Profile Picture",
                            placeholder = painterResource(R.drawable.pokeball_icon),
                            modifier = Modifier.size(200.dp),
                            error = painterResource(R.drawable.pokeball_icon)
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .shadow(
                                8.dp,
                                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                            )
                            .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                            .background(MaterialTheme.colorScheme.background)
                            .padding(32.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        DataRow("Name:", uiState.name ?: "N/A", fontSize = 20.sp)
                        DataRow("Surname:", uiState.lastName ?: "N/A", fontSize = 20.sp)
                        DataRow("Email:", uiState.email ?: "N/A", fontSize = 20.sp)
                        Column(
                            Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Bottom,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            TextButton(
                                onClick = {
                                    viewModel.signOut()
                                    onSignOut()
                                },
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("Logout", fontSize = 18.sp, color = LogoutColor)
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.Logout,
                                        contentDescription = "Logout button",
                                        Modifier.size(24.dp),
                                        tint = LogoutColor
                                    )
                                }
                            }
                        }
                    }
                }
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

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(onSignOut = {}, onGoBack = {})
}