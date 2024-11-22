package com.example.pokedex.di

import com.example.pokedex.data.FirebaseAuthRepository
import com.example.pokedex.data.FirestoreUserRepository
import com.example.pokedex.data.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideFirebaseAuthRepository(): FirebaseAuthRepository {
        return FirebaseAuthRepository()
    }

    @Provides
    @Singleton
    fun provideFirestoreUserRepository(): FirestoreUserRepository {
        return FirestoreUserRepository()
    }

    @Provides
    @Singleton
    fun providePokemonRepository(): PokemonRepository {
        return PokemonRepository
    }
}