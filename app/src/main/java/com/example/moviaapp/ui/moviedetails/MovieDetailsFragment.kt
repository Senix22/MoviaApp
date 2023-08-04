package com.example.moviaapp.ui.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.moviaapp.R
import com.example.moviaapp.common.State
import com.example.moviaapp.data.models.MovieDetailEntity
import com.example.moviaapp.data.models.MovieDetailsResult
import com.example.moviaapp.data.models.MovieGenresEntity
import com.example.moviaapp.data.models.MovieItemResult
import com.example.moviaapp.databinding.DetailsFragmentBinding
import com.example.moviaapp.databinding.TrailerFragmentBinding
import com.example.moviaapp.ui.trailer.TrailerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private val viewModel: MovieDetailsViewModel by viewModels()
    private lateinit var binding: DetailsFragmentBinding
    private var movieId = 0L
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieId = arguments?.getLong("movieId") ?: 0L
        getMovie(movieId.toString())

    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            viewModel.stateFlow.collect { uiState ->
                when (uiState) {
                    is State.Content -> {
                        when (uiState.data) {
                            is MovieDetailsResult.Failure -> {
                                println()
                            }

                            is MovieDetailsResult.Success -> {

                                setUi(uiState.data.result)
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

                    }
                }
            }
        }

    }

    private fun getMovie(id: String) {
        viewModel.getDetails(id)
    }

    private fun setUi(move: MovieDetailEntity) {
        binding.title.text = move.originalTitle
        binding.rating.text = move.voteAverage.toString().take(3)
        binding.movieStatus.text = move.status
        binding.overviewText.text = move.overview
        binding.release.text = "Release: " + move.releaseDate
        binding.play.setOnClickListener {
            openTrailer(movieId)
        }
        Glide.with(requireContext())
            .load("https://image.tmdb.org/t/p/w600_and_h900_bestv2${move.posterPath}")
            .into(binding.poster)

        setGenreAdapter(move.genres ?: emptyList())
    }

    private fun setGenreAdapter(list: List<MovieGenresEntity>) {
        binding.genreRecycler.adapter = GenreAdapter(list)
    }

    private fun openTrailer(id: Long) {
        val bundle = bundleOf("movieId" to id)
        findNavController().navigate(R.id.action_show_trailer, bundle)
    }
}