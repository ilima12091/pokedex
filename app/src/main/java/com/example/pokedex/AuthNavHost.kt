package com.example.pokedex

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokedex.ui.screens.auth.CreateUserScreen
import com.example.pokedex.ui.screens.auth.LoginScreen

@Composable
fun AuthNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    onNavigateToMain: () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier
    ) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = { onNavigateToMain() },
                onNavigateToCreateUser = { navController.navigate("createUser") },
            )
        }
        composable("createUser") {
            CreateUserScreen(
                onUserCreated = { },
                onBackToLogin = { navController.popBackStack() },
            )
        }
    }
}