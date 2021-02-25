package com.example.aguiarmovies.activities

import OMDbServiceInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.aguiarmovies.R
import com.example.aguiarmovies.adapters.OMDbService
import com.example.aguiarmovies.models.Movie
import com.example.aguiarmovies.models.MoviesResponse
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListeners()
    }

    fun setListeners(){
        btSearch.setOnClickListener{
            getMovies("teste")
        }
    }

    fun setMoviesList(moviesList: List<Movie>){
        println(moviesList)
    }

    fun getMovies(name: String){
        val omdbService: OMDbServiceInterface = OMDbService().getService()

        omdbService.listRepos("43961fbe").enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
                var moviesResponse: MoviesResponse = response.body()!!
                setMoviesList(moviesResponse.search)
            }
            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                throw t
            }
        })
    }
}