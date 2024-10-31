package com.example.phumlanidubefoundationmobileapplication.ui.newsletter

import android.os.Bundle
import com.example.phumlanidubefoundationmobileapplication.R
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

class MainActivity : AppCompatActivity() {

    private val viewModel: NewsletterViewModel by viewModels()
    private lateinit var playerView: PlayerView
    private lateinit var player: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Media3 PlayerView and ExoPlayer
        playerView = findViewById(R.id.player_view)
        player = ExoPlayer.Builder(this).build()
        playerView.player = player

        // Observe data from ViewModel
        viewModel.categories.observe(this) { categories ->
            // Update your UI with categories
        }

        viewModel.events.observe(this) { events ->
            // Update your UI with events
        }

        viewModel.announcements.observe(this) { announcements ->
            // Update your UI with announcements
        }

        // Fetch data
        viewModel.fetchCategories()
        viewModel.fetchEventsByCategory("August") // Example: Fetch events for August
        viewModel.fetchAnnouncements()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()  // Release the player when done
    }
}

