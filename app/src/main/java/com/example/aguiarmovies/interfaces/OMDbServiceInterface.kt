import com.example.aguiarmovies.models.Movie
import com.example.aguiarmovies.models.MoviesResponse

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val apiKey ="43961fbe"

interface OMDbServiceInterface {
    @GET("?apiKey=$apiKey")
    fun listMovies(@Query("s") title: String?): Call<MoviesResponse>

    @GET("?apiKey=$apiKey")
    fun getMovie(@Query("i") imdbID: String?): Call<Movie>
}