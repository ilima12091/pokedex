package com.example.pokedex.ui.utils

object Constants {
    const val BASE_URL = "https://pokeapi.co/api/v2/"
    const val DEFAULT_POKEMON_LIMIT_FOR_HOME_PAGE = 20

    object FirestoreKeys {
        const val USERS_COLLECTION = "users"
        const val FAVORITES_FIELD = "favorites"
        const val PROFILE_PICTURE_FIELD = "profilePictureUrl"
    }

    object ErrorMessages {
        const val USER_NOT_LOGGED_IN = "User is not logged in."
        const val FAILED_TO_FETCH_DATA = "Failed to fetch data. Please try again."
        const val PASS_DO_NOT_MATCH = "Passwords do not match."
        const val EMAIL_AND_PASS_CANNOT_BE_EMPTY = "Email and Password cannot be empty"
        const val FAILED_TO_SAVE_USER_DETAILS = "Failed to save user details"
        const val FAILED_TO_RETRIEVE_USER_ID = "Failed to retrieve user ID"
        const val FAILED_TO_CREATE_ACCOUNT = "Failed to create account"
        const val FAILED_TO_LOGIN = "Failed to login"
        const val INVALID_CREDENTIALS = "Invalid email or password. Please try again."
        const val FAILED_TO_PERFORM_ACTION = "Failed to perform action. Please try again"
        const val FAILED_TO_FETCH_FAVORITES = "Failed to fetch favorites."

    }

    object UiConstants {
        const val LOADING_INDICATOR_SIZE = 48
    }

    object Pokemon {
        const val LAST_POKEMON_ORDER = 1010
        const val FETCH_POKEMON_OFFSET = 0
        const val FETCH_POKEMON_PAGE_SIZE = 20
    }
}