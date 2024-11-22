package com.example.pokedex.api

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

object FirebaseAuthClient {
    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    suspend fun login(email: String, password: String): FirebaseUser? {
        val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        return authResult.user
    }

    suspend fun register(email: String, password: String): FirebaseUser? {
        val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        return authResult.user
    }

    fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    fun signOut() {
        firebaseAuth.signOut()
    }
}