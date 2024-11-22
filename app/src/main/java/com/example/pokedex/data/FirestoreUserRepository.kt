package com.example.pokedex.data

import android.util.Log
import com.example.pokedex.api.FirestoreClient
import com.example.pokedex.data.models.FavoritePokemon
import com.example.pokedex.data.models.User
import kotlinx.coroutines.tasks.await

class FirestoreUserRepository : UserRepository {

    private val usersCollection = FirestoreClient.instance.collection("users")

    override suspend fun getUser(uid: String): Result<User> {
        return try {
            val document = usersCollection.document(uid).get().await()
            val user = document.toObject(User::class.java)
            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(Exception("User not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun saveUser(uid: String, user: User): Result<Unit> {
        return try {
            usersCollection.document(uid).set(user).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getFavorites(uid: String): Result<List<FavoritePokemon>> {
        return try {
            val document = usersCollection.document(uid).get().await()
            val favorites = document.toObject(User::class.java)?.favorites ?: emptyList()
            Log.d("FirestoreRepository", "favorites is: $favorites")
            Result.success(favorites)
        } catch (e: Exception) {
            Log.e("FirestoreRepository", "Error fetching favorites for user $uid: ${e.message}", e)
            Result.failure(e)
        }
    }

    override suspend fun updateFavorites(uid: String, favorites: List<FavoritePokemon>): Result<Unit> {
        return try {
            usersCollection.document(uid).update("favorites", favorites).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}