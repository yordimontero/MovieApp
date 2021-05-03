package com.example.movieapp.ui.movie

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.movieapp.R
import com.example.movieapp.core.Resource
import com.example.movieapp.data.remote.MovieDataSource
import com.example.movieapp.databinding.FragmentMovieBinding
import com.example.movieapp.presentation.MovieViewModel
import com.example.movieapp.presentation.ViewModelFactory
import com.example.movieapp.repository.MovieRepositoryImpl
import com.example.movieapp.repository.RetrofitClient

class MovieFragment : Fragment(R.layout.fragment_movie) {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)

        fetchUpcomingMoviesObserver()

    }

    private fun fetchUpcomingMoviesObserver() {

        viewModel.fetchMainScreenMovies()
                .observe(viewLifecycleOwner, Observer { resultEmitted ->

                    when (resultEmitted) {

                        is Resource.Loading -> {
                            Log.wtf("ResultEmitted", "Loading...")
                        }

                        is Resource.Success -> {
                            Log.wtf("Upcoming: ", "${resultEmitted.data.t1} \n \n")
                            Log.wtf("Popular: ", "${resultEmitted.data.t2} \n \n")
                            Log.wtf("Top Rated: ", "${resultEmitted.data.t3}")
                        }

                        is Resource.Failure -> {
                            Log.wtf("ResultEmitted", "${resultEmitted.exception}")
                        }

                    }

                })

    }

}