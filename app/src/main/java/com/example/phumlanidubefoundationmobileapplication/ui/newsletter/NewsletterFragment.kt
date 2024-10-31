package com.example.phumlanidubefoundationmobileapplication.ui.newsletter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.phumlanidubefoundationmobileapplication.R
import com.example.phumlanidubefoundationmobileapplication.databinding.FragmentNewsletterBinding
import android.widget.TextView
import android.view.MotionEvent
import android.view.GestureDetector
import androidx.fragment.app.viewModels
import com.example.phumlanidubefoundationmobileapplication.BaseActivity
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.google.firebase.firestore.FirebaseFirestore



class NewsletterFragment : Fragment() {

    private lateinit var binding: FragmentNewsletterBinding
    private val viewModel: NewsletterViewModel by viewModels()
    private lateinit var playerView: PlayerView
    private lateinit var player: ExoPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewsletterBinding.inflate(inflater, container, false)

        // Initialize Media3 PlayerView and ExoPlayer
        playerView = binding.playerView  // Access playerView from the binding
        player = ExoPlayer.Builder(requireContext()).build()
        playerView.player = player

        // Access BaseActivity and apply font size changes if needed
        (activity as? BaseActivity)?.applyFontSize()  // Call the font size method from BaseActivity

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe data from ViewModel
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            // Update your UI with categories
        }

        viewModel.events.observe(viewLifecycleOwner) { events ->
            // Update your UI with events
        }

        viewModel.announcements.observe(viewLifecycleOwner) { announcements ->
            // Update your UI with announcements
        }

        // Fetch data
        viewModel.fetchCategories()
        viewModel.fetchEventsByCategory("August") // Example: Fetch events for August
        viewModel.fetchAnnouncements()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        player.release()  // Release the player when done
    }
}

