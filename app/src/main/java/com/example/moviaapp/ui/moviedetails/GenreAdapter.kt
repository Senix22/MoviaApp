package com.example.moviaapp.ui.moviedetails

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviaapp.data.models.MovieGenresEntity
import com.example.moviaapp.databinding.GenreItemBinding
import com.example.moviaapp.ui.viewholder.GenreViewHolder

class GenreAdapter(private val items: List<MovieGenresEntity>) :
    RecyclerView.Adapter<GenreViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder(
            GenreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}