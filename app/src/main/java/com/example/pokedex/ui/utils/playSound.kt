package com.example.pokedex.ui.utils

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import java.io.IOException

fun playSound(url: String, context: Context) {
    val mediaPlayer = MediaPlayer()
    mediaPlayer.setAudioAttributes(
        AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()
    )

    try {
        mediaPlayer.setDataSource(
            context,
            Uri.parse(url)
        )
        mediaPlayer.prepare()
        mediaPlayer.start()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}