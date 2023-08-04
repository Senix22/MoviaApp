package com.example.moviaapp.ui.trailer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.moviaapp.common.State
import com.example.moviaapp.data.models.MovieItemResult
import com.example.moviaapp.databinding.TrailerFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener


import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TrailerFragment : BottomSheetDialogFragment() {
    private val movieViewModel: TrailerViewModel by viewModels()
    private lateinit var binding: TrailerFragmentBinding
    private var movieId = 0L
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = TrailerFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieId = arguments?.getLong("movieId") ?: 0L
        getMovie(movieId.toString())

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            // Handle the back button event
            findNavController().popBackStack()
        }
    }


    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            movieViewModel.stateFlow.collect { uiState ->
                when (uiState) {
                    is State.Content -> {
                        when (uiState.data) {
                            is MovieItemResult.Failure -> {
                                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                            }

                            is MovieItemResult.Success -> {
                                val trailer =
                                    uiState.data.result.firstOrNull { it.name.contains("Trailer") }

                                trailer?.apply {
                                    startTrailer(key)
                                }

                            }
                        }

                    }

                    State.Empty -> {
                        Toast.makeText(requireContext(), "Empty", Toast.LENGTH_SHORT).show()
                    }

                    is State.Error -> {
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }

                    State.Loading -> {
                        Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    private fun getMovie(id: String) {
        movieViewModel.getMovieItem(id)
    }

    private fun startTrailer(videoKey: String) {
        binding.youtubePlayerView.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                youTubePlayer.loadVideo(videoKey, 0f)
            }
        })
    }
}