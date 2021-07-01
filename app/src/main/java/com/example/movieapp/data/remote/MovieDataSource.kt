package com.example.movieapp.data.remote

import com.example.movieapp.application.AppConstants
import com.example.movieapp.data.model.MovieList
import com.example.movieapp.repository.WebService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieDataSource(private val webService: WebService) {

    suspend fun getUpcomingMovies(): MovieList = withContext(Dispatchers.IO) {
        webService.getUpcomingMovies(AppConstants.API_KEY)
    }

    suspend fun getTopRatedMovies(): MovieList = withContext(Dispatchers.IO) {
        webService.getTopRatedMovies(AppConstants.API_KEY)
    }

    suspend fun getPopularMovies(): MovieList = withContext(Dispatchers.IO) {
        webService.getPopularMovies(AppConstants.API_KEY)
    }

}