import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import com.example.playlistapp.MainActivity
import com.example.playlistapp.artistList
import com.example.playlistapp.commentList
import com.example.playlistapp.ratingList
import com.example.playlistapp.songList

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DetailScreen()
        }
    }
}

@Composable
fun DetailScreen() {
    var averageRating by remember { mutableStateOf(0.0) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Playlist Details", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(10.dp))

        // Button to display all songs
        Button(onClick = { /* Recomposition will display list below */ }) {
            Text("Display Songs")
        }

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn {
            items(songList.size) { i ->
                Text("🎵 ${songList[i]} - ${artistList[i]} (Rating: ${ratingList[i]}, Comment: ${commentList[i]})")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Button to calculate average rating
        Button(onClick = {
            averageRating = calculateAverageRating()
        }) {
            Text("Calculate Average Rating")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text("Average Rating: $averageRating")

        Spacer(modifier = Modifier.height(20.dp))

        // Button to go back to MainActivity
        val context = LocalContext.current
        Button(onClick = {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }) {
            Text("Back to Main Screen")
        }
    }
}

// Average rating calculation
fun calculateAverageRating(): Double {
    return if (ratingList.isNotEmpty()) {
        ratingList.sum().toDouble() / ratingList.size
    } else 0.0
}