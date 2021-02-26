import com.example.aguiarmovies.models.MoviesResponse

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OMDbServiceInterface {
    @GET("?")
    fun listMovies(@Query("apiKey") apiKey: String?, @Query("s") title: String?): Call<MoviesResponse>
}