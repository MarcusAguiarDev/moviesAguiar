package com.example.aguiarmovies.models

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("Search") var search: List<Movie>,
    @SerializedName("totalResults") var totalResults: Number,
    @SerializedName("Response") var response: Boolean
)