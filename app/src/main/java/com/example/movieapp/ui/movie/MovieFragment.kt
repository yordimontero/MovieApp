package com.example.movieapp.ui.movie

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.example.movieapp.R
import com.example.movieapp.core.Resource
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.remote.MovieDataSource
import com.example.movieapp.databinding.FragmentMovieBinding
import com.example.movieapp.presentation.MovieViewModel
import com.example.movieapp.presentation.ViewModelFactory
import com.example.movieapp.repository.MovieRepositoryImpl
import com.example.movieapp.repository.RetrofitClient
import com.example.movieapp.ui.movie.adapters.MovieAdapter
import com.example.movieapp.ui.movie.adapters.concat.PopularConcatAdapter
import com.example.movieapp.ui.movie.adapters.concat.TopRatedConcatAdapter
import com.example.movieapp.ui.movie.adapters.concat.UpcomingConcatAdapter

class MovieFragment : Fragment(R.layout.fragment_movie), MovieAdapter.OnMovieClickListener {

    private lateinit var binding: FragmentMovieBinding

    private val viewModel by viewModels<MovieViewModel> {
        ViewModelFactory(
                MovieRepositoryImpl(
                        MovieDataSource(
                                RetrofitClient.webService
                        )
                )
        )
    }

    private lateinit var concatAdapter: ConcatAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)

        concatAdapter = ConcatAdapter()

        fetchUpcomingMoviesObserver()

    }

    private fun fetchUpcomingMoviesObserver() {

        viewModel.fetchMainScreenMovies()
                .observe(viewLifecycleOwner, Observer { resultEmitted ->

                    when (resultEmitted) {

                        is Resource.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is Resource.Success -> {

                            concatAdapter.apply {
                                addAdapter(0,
                                        UpcomingConcatAdapter(
                                                MovieAdapter(
                                                        resultEmitted.data.t1.results,
                                                        this@MovieFragment
                                                )
                                        )
                                )
                            }

                            concatAdapter.apply {
                                addAdapter(1,
                                        PopularConcatAdapter(
                                                MovieAdapter(
                                                        resultEmitted.data.t2.results,
                                                        this@MovieFragment
                                                )
                                        )
                                )
                            }

                            concatAdapter.apply {
                                addAdapter(0,
                                        TopRatedConcatAdapter(
                                                MovieAdapter(
                                                        resultEmitted.data.t3.results,
                                                        this@MovieFragment
                                                )
                                        )
                                )
                            }

                            binding.rvMovies.adapter = concatAdapter

                            binding.progressBar.visibility = View.GONE
                        }

                        is Resource.Failure -> {
                            binding.progressBar.visibility = View.GONE
                        }

                    }

                })

    }

    override fun onMovieClick(movie: Movie) {

        val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(
                movie.poster_path,
                movie.backdrop_path,
                movie.vote_average.toFloat(),
                movie.vote_count,
                movie.overview,
                movie.title,
                movie.original_language,
                movie.release_date
        )

        findNavController().navigate(action)

    }

}