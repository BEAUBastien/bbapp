package com.example.bbapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory



class MainViewModel : ViewModel() {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build();
    val api = retrofit.create(Api::class.java)
    val movies = MutableStateFlow<List<TmdbMovie>>(listOf())

    fun getMovies() {
        viewModelScope.launch {
            movies.value = api.lastmovies("c36d23110d3cb6185a16058a84974221").results
        }
    }
}

