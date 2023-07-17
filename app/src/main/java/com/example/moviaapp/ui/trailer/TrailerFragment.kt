package com.example.moviaapp.ui.trailer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.moviaapp.common.State
import com.example.moviaapp.data.MovieItemResult
import com.example.moviaapp.databinding.TrailerFragmentBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener


import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TrailerFragment : Fragment() {
    private val movieViewModel: TrailerViewModel by viewModels()
    private lateinit var binding: TrailerFragmentBinding
    var movieId = 0L
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = TrailerFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieId = arguments?.getLong("id") ?: 1232L
        getMovie(movieId.toString())

    }


    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            movieViewModel.stateFlow.collect { uiState ->
                when (uiState) {
                    is State.Content -> {
                        when (uiState.data) {
                            is MovieItemResult.Failure -> {
                                println()
                            }

                            is MovieItemResult.Success -> {
                                val trailer =
                                    uiState.data.result.firstOrNull { it.name.contains("Trailer") }

                                trailer?.apply {
                                    binding.text.text = name
                                    startTrailer(key)
                                }

                                //youtube https://www.youtube.com/watch?v= ++ KEY
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