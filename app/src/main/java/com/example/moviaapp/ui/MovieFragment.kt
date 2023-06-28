package com.example.moviaapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviaapp.databinding.MovieFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieFragment : Fragment() {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: MovieFragmentBinding

    lateinit var adapter: RemoteDoggoImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MovieFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            mainViewModel.news.distinctUntilChanged().collectLatest {
                it
//                adapter.submitData(it)
            }
        }
    }


    private fun setUpViews() {
        binding.rvSubject.apply {
            layoutManager = LinearLayoutManager(requireContext())
//            adapter = RemoteDoggoImageAdapter()
        }
    }
}