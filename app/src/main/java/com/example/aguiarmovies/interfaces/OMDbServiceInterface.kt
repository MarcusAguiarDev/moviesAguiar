import com.example.aguiarmovies.models.MoviesResponse

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OMDbServiceInterface {
    @GET("?s=spider&page=2")
    fun listRepos(@Query("apiKey") apiKey: String?): Call<MoviesResponse>
}