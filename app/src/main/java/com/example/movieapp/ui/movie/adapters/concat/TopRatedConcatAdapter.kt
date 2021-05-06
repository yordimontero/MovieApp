package com.example.movieapp.ui.movie.adapters.concat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.core.BaseConcatHolder
import com.example.movieapp.databinding.TopRatedMoviesBinding
import com.example.movieapp.ui.movie.adapters.MovieAdapter

class TopRatedConcatAdapter(
        private val moviesAdapter: MovieAdapter
) : RecyclerView.Adapter<BaseConcatHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseConcatHolder<*> {

        val itemBinding = TopRatedMoviesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )

        return ConcatViewHolder(itemBinding)

    }

    override fun onBindViewHolder(holder: BaseConcatHolder<*>, position: Int) {

        when(holder) {

            is ConcatViewHolder -> {
                holder.bind(moviesAdapter)
            }

        }

    }

    override fun getItemCount(): Int = 1

    private inner class ConcatViewHolder(
            val binding: TopRatedMoviesBinding
    ) : BaseConcatHolder<MovieAdapter>(binding.root) {

        override fun bind(adapter: MovieAdapter) {
            binding.rvTopRatedMovies.adapter = adapter
        }

    }

}