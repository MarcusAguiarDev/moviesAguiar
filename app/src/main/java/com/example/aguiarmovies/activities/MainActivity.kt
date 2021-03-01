package com.example.aguiarmovies.activities

import OMDbServiceInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.aguiarmovies.R
import com.example.aguiarmovies.adapters.OMDbService
import com.example.aguiarmovies.adapters.RecycleViewAdapter
import com.example.aguiarmovies.models.Movie
import com.example.aguiarmovies.models.MoviesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var btSearch: Button
    private lateinit var rvList: RecyclerView
    private lateinit var editName: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //UI binding
        btSearch = findViewById(R.id.btSearch)
        rvList = findViewById(R.id.rvMovieList)
        editName = findViewById(R.id.editName)

        //set listeners
        setListeners()
    }

    fun setListeners() {
        btSearch.setOnClickListener {
            getMovies(editName.text.toString())
        }

        //on "ENTER" getMovies
        editName.setOnKeyListener(View.OnKeyListener{ v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                getMovies(editName.text.toString())
            }
            false
        })
    }

    fun setMoviesList(moviesList: List<Movie>) {
        //set adapter
        rvList.adapter = RecycleViewAdapter(moviesList)
    }

    fun getMovies(name: String) {

        val omdbService: OMDbServiceInterface = OMDbService().getService()

        omdbService.listMovies(name).enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {

                var moviesResponse: MoviesResponse = response.body()!!

                moviesResponse?.search?.let {
                    setMoviesList(moviesResponse.search)
                }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                throw t
            }
        })
    }
}