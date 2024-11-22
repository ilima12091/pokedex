package com.example.pokedex.data

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<FirebaseUser?>
    suspend fun register(email: String, password: String): Result<FirebaseUser?>
    suspend fun getCurrentUserOrThrow(): FirebaseUser
    fun getCurrentUser(): FirebaseUser?
    fun signOut()
}