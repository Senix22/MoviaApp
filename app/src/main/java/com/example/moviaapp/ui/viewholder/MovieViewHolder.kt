package com.example.moviaapp.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.moviaapp.data.models.MovieEntity
import com.example.moviaapp.databinding.MovieItemBinding

class MovieViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: MovieEntity?) {
        binding.text.text = item?.title.orEmpty()
        binding.ratingText.text = item?.rating?.toString()
    }
}