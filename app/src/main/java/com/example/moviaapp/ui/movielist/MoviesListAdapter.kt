package com.example.moviaapp.ui.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.moviaapp.data.MovieEntity
import com.example.moviaapp.databinding.MovieItemBinding
import com.example.moviaapp.ui.viewholder.MovieViewHolder

class MoviesListAdapter(var loadNextData: (position: Long) -> Unit) :
    PagingDataAdapter<MovieEntity, MovieViewHolder>(MovieComparator) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        return MovieViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)
        // Note that item may be null. ViewHolder must support binding a
        // null item as a placeholder.\

        with(holder.bind(item)) {
            Glide.with(holder.itemView.context)
                .load("https://image.tmdb.org/t/p/w600_and_h900_bestv2${item?.backdropPath}")
                .into(holder.binding.imageView)
            holder.binding.imageView.setOnClickListener {
                loadNextData(item?.id ?: 0L)
            }
        }


    }
}

object MovieComparator : DiffUtil.ItemCallback<MovieEntity>() {
    override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
        // Id is unique.
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
        return oldItem == newItem
    }
}