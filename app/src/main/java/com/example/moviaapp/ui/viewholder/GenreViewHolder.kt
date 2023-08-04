package com.example.moviaapp.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.moviaapp.data.models.MovieGenresEntity
import com.example.moviaapp.databinding.GenreItemBinding

class GenreViewHolder(val binding: GenreItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(genre: MovieGenresEntity) {
        binding.genreText.text = genre.name
    }
}