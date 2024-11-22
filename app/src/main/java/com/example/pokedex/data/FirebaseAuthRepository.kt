package com.example.pokedex.data

import com.example.pokedex.api.FirebaseAuthClient
import com.google.firebase.auth.FirebaseUser

class FirebaseAuthRepository: AuthRepository {

    override suspend fun login(email: String, password: String): Result<FirebaseUser?> {
        return try {
            val user = FirebaseAuthClient.login(email, password)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(email: String, password: String): Result<FirebaseUser?> {
        return try {
            val user = FirebaseAuthClient.register(email, password)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun signOut() {
        FirebaseAuthClient.signOut()
    }

    override fun getCurrentUser(): FirebaseUser? {
        return FirebaseAuthClient.getCurrentUser()
    }
}