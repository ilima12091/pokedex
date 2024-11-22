package com.example.pokedex.data

import com.example.pokedex.data.models.FavoritePokemon
import com.example.pokedex.data.models.User

interface UserRepository {
    suspend fun updateUserProfilePicture(uid: String, imageUrl: String): Result<Unit>
    suspend fun getUser(uid: String): Result<User>
    suspend fun saveUser(uid: String, user: User): Result<Unit>
    suspend fun getFavorites(uid: String): Result<List<FavoritePokemon>>
    suspend fun updateFavorites(uid: String, favorites: List<FavoritePokemon>): Result<Unit>
    suspend fun getProfilePictureUrl(uid: String): Result<String?>
}