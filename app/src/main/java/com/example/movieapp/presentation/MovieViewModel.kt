package com.example.movieapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.movieapp.core.Resource
import com.example.movieapp.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    fun fetchUpcomingMovies() = liveData(Dispatchers.IO) {

        emit(Resource.Loading())

        try {
            emit(Resource.Success(repository.getUpcomingMovies()))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }

    }

}

class ViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>)
            : T = modelClass.getConstructor(MovieRepository::class.java).newInstance(repository)

}