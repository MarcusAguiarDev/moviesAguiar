package com.example.aguiarmovies.adapters

import OMDbServiceInterface

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OMDbService {

    private val baseUrl:String = "http://www.omdbapi.com/"
    private val API_KEY = "43961fbe"

    fun getService(): OMDbServiceInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(OMDbServiceInterface::class.java)
    }
}