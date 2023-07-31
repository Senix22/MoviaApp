package com.example.moviaapp.ui.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviaapp.R
import com.example.moviaapp.common.State
import com.example.moviaapp.common.hide
import com.example.moviaapp.common.show
import com.example.moviaapp.data.MovieEntity
import com.example.moviaapp.databinding.MovieListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieFragment : Fragment() {
    private val movieListViewModel: MovieListViewModel by viewModels()
    private lateinit var binding: MovieListFragmentBinding

    private var adapter: MoviesListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MovieListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        collectState()
        binding.search.setOnQueryTextListener(object : OnQueryTextListener,
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true

            }

            override fun onQueryTextChange(text: String?): Boolean {
                text?.let {
                    searchMovie(it)
                }
                return true

            }

        })
        lifecycleScope.launch {
            movieListViewModel.state.collect { state ->
                when (state) {
                    is State.Content -> {
                        binding.rvSubject.show()
                    }

                    State.Empty -> binding.rvSubject.hide()
                    is State.Error -> {
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }

                    State.Loading -> {
                        binding.pBar.show()
                    }
                }
            }
        }
    }

    private fun initView() {
        adapter = MoviesListAdapter { id ->
            openTrailer(id)
        }
        binding.rvSubject.adapter = adapter
        binding.rvSubject.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun collectState() {
        viewLifecycleOwner.lifecycleScope.launch {
            movieListViewModel.movieList.collectLatest {
                adapter?.submitData(it)
            }
        }
    }

    private fun searchMovie(search: String) {
        movieListViewModel.searchMovie(search)
    }

    private fun openTrailer(id: Long) {
        val bundle = bundleOf("movieId" to id)
        findNavController().navigate(R.id.action_show_movie, bundle)
    }
}