package com.example.pokedex.api

import com.google.firebase.firestore.FirebaseFirestore

object FirestoreClient {
    val instance: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
}