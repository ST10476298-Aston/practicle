package com.example.playlistapp

import DetailActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlin.system.exitProcess

// Parallel arrays to hold the data
val songList = mutableListOf<String>()
val artistList = mutableListOf<String>()
val ratingList = mutableListOf<Int>()
val commentList = mutableListOf<String>()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    var songTitle by remember { mutableStateOf("") }
    var artistName by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf("") }
    var comment by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Add to Playlist", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(10.dp))

        // Text fields for input
        OutlinedTextField(value = songTitle, onValueChange = { songTitle = it }, label = { Text("Song Title") })
        OutlinedTextField(value = artistName, onValueChange = { artistName = it }, label = { Text("Artist Name") })
        OutlinedTextField(value = rating, onValueChange = { rating = it }, label = { Text("Rating (1-5)") })
        OutlinedTextField(value = comment, onValueChange = { comment = it }, label = { Text("Comment") })

        Spacer(modifier = Modifier.height(10.dp))

        // Button to add song to playlist
        Button(onClick = {
            if (songTitle.isNotBlank() && artistName.isNotBlank() && rating.isNotBlank() && isValidRating(rating)) {
                addSong(songTitle, artistName, rating.toInt(), comment)
                songTitle = ""
                artistName = ""
                rating = ""
                comment = ""
            } else {
                Toast.makeText(
                    MainActivity().applicationContext,
                    "Please enter valid details",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }) {
            Text("Add to Playlist")
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Button to go to Detail screen
        Button(onClick = {
            val context = LocalContext.current
            val intent = Intent(context, DetailActivity::class.java)
            context.startActivity(intent)
        }) {
            Text("Go to Details")
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Exit button
        Button(onClick = {
            exitProcess(0)
        }) {
            Text("Exit App")
        }
    }
}

// Add song to all arrays
fun addSong(song: String, artist: String, rating: Int, comment: String) {
    songList.add(song)
    artistList.add(artist)
    ratingList.add(rating)
    commentList.add(comment)
}

// Validate rating input
fun isValidRating(input: String): Boolean {
    return try {
        val rate = input.toInt()
        rate in 1..5
    } catch (e: Exception) {
        false
    }
}
