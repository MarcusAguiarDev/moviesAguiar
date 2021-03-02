package com.example.aguiarmovies.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.aguiarmovies.R
import com.example.aguiarmovies.services.OMDbService
import com.example.aguiarmovies.models.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.NumberFormatException

const val NA: String = "N/A"

class MovieDetail : AppCompatActivity() {

    private lateinit var imdbID: String
    private lateinit var cl_movie_detail: ConstraintLayout
    private lateinit var tv_title: TextView
    private lateinit var tv_year: TextView
    private lateinit var tv_genre: TextView
    private lateinit var tv_released: TextView
    private lateinit var tv_runtime: TextView
    private lateinit var tv_director: TextView
    private lateinit var tv_writer: TextView
    private lateinit var tv_actors: TextView
    private lateinit var tv_plot: TextView
    private lateinit var tv_language: TextView
    private lateinit var tv_country: TextView
    private lateinit var tv_awards: TextView
    private lateinit var iv_poster: ImageView
    private lateinit var tv_imdbRating: TextView
    private lateinit var tv_imdbVotes: TextView
    private lateinit var rb_rating: RatingBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        //get "extras" from intent
        val inputImdbID = "imdbID"
        imdbID = (intent.getStringExtra(inputImdbID)).toString()

        getMovieDetail(imdbID)

        //bind view
        tv_title = findViewById(R.id.tv_title)
        cl_movie_detail = findViewById(R.id.cl_movie_detail)
        tv_year = findViewById(R.id.tv_year)
        tv_genre = findViewById(R.id.tv_genre)
        tv_released = findViewById(R.id.tv_released)
        tv_runtime = findViewById(R.id.tv_runtime)
        tv_director = findViewById(R.id.tv_director)
        tv_writer = findViewById(R.id.tv_writer)
        tv_actors = findViewById(R.id.tv_actors)
        tv_plot = findViewById(R.id.tv_plot)
        tv_language = findViewById(R.id.tv_language)
        tv_country = findViewById(R.id.tv_country)
        tv_awards = findViewById(R.id.tv_awards)
        iv_poster = findViewById(R.id.iv_poster)
        tv_imdbRating = findViewById(R.id.tv_imdbRating)
        tv_imdbVotes = findViewById(R.id.tv_imdbVotes)
        rb_rating = findViewById(R.id.rb_rating)
    }

    private fun getMovieDetail(imdbID: String?) {
        if (!imdbID.isNullOrEmpty()) {
            val omdbService = OMDbService().getService()

            omdbService.getMovie(imdbID).enqueue(object : Callback<Movie> {
                override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                    val movie: Movie? = response.body()
                    fillFields(movie)
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    Log.e("catched_error", t.message.toString())
                }
            })
        }
    }

    private fun fillFields(movie: Movie?) {
        //check class
        if (movie is Movie) {
            tv_title.text = movie.title
            tv_year.text = movie.year
            tv_genre.text = getString(R.string.genre, movie.genre)
            tv_released.text = getString(R.string.released, movie.released)
            tv_runtime.text = getString(R.string.runtime, movie.runtime)
            tv_director.text = getString(R.string.director, movie.director)
            tv_writer.text = getString(R.string.writer, movie.writer)
            tv_actors.text = getString(R.string.actors, movie.actors)
            tv_plot.text = getString(R.string.plot, movie.plot)
            tv_language.text = getString(R.string.language, movie.language)
            tv_country.text = getString(R.string.country, movie.country)
            tv_awards.text = getString(R.string.awards, movie.awards)
            tv_imdbRating.text = getString(R.string.rating, movie.rating)
            tv_imdbVotes.text = getString(R.string.votes, movie.votes)

            //load image from uri
            if (movie.poster != NA)
                Glide.with(this).load(movie.poster).into(iv_poster)
            else
                iv_poster.setImageResource(R.drawable.no_image_available)

            //set stars
            //if "N/A" set 0 stars
            try {
                rb_rating.stepSize = 0.1F
                rb_rating.rating = (movie.rating.toFloat() / 2)
            } catch (e: NumberFormatException) {
                rb_rating.rating = 0.0F
            }
        }
    }
}