package com.example.aguiarmovies.activities

import android.graphics.Typeface.BOLD
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.aguiarmovies.R
import com.example.aguiarmovies.adapters.OMDbService
import com.example.aguiarmovies.models.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetail : AppCompatActivity() {

    lateinit var imdbID: String
    lateinit var cl_movie_detail: ConstraintLayout
    lateinit var tv_title: TextView
    lateinit var tv_year: TextView
    lateinit var tv_genre: TextView
    lateinit var tv_released: TextView
    lateinit var tv_runtime: TextView
    lateinit var tv_director: TextView
    lateinit var tv_writer: TextView
    lateinit var tv_actors: TextView
    lateinit var tv_plot: TextView
    lateinit var tv_language: TextView
    lateinit var tv_country: TextView
    lateinit var tv_awards: TextView
    lateinit var iv_poster: ImageView
    lateinit var tv_imdbRating: TextView
    lateinit var tv_imdbVotes: TextView
    lateinit var rb_rating: RatingBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        imdbID = (intent.getStringExtra("imdbID")).toString()

        getMovieDetail(imdbID!!)

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

    fun getMovieDetail(imdbID: String?){
        if(!imdbID.isNullOrEmpty()){
            val omdbService = OMDbService().getService()

            omdbService.getMovie(imdbID).enqueue(object: Callback<Movie> {
                override fun onResponse(call: Call<Movie>, response: Response<Movie>){
                    val movie: Movie = response.body()!!

                    fillFields(movie)
                }

                override fun onFailure(call: Call<Movie>, t: Throwable){

                }
            })
        }
    }

    fun fillFields(movie: Movie){
        tv_title.text = movie.title
        tv_year.text = movie.year
        tv_genre.text = "Gênero: ${movie.genre}"
        tv_released.text = "Data de lançamento: ${movie.released}"
        tv_runtime.text = "Duração: ${movie.runtime}"
        tv_director.text = "Diretor: ${movie.director}"
        tv_writer.text = "Escritor: ${movie.writer}"
        tv_actors.text = "Elenco: ${movie.actors}"
        tv_plot.text = "Sinopse: ${movie.plot}"
        tv_language.text = "Idioma: ${movie.language}"
        tv_country.text = "País: ${movie.country}"
        tv_awards.text = "Prêmios: ${movie.awards}"
        tv_imdbRating.text = "Avaliação: ${movie.rating}"
        tv_imdbVotes.text = "Quantidade de votos: ${movie.votes}"

        //load image from uri
        if(movie.poster != "N/A")
            Glide.with(this).load(movie.poster).into(iv_poster)
        else
            iv_poster.setImageResource(R.drawable.default_image)

        //set stars
        rb_rating.stepSize = 0.1F
        rb_rating.rating = (movie.rating.toFloat() / 2)

    }
}