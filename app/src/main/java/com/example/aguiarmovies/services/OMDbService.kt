package com.example.aguiarmovies.services

import OMDbServiceInterface

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://www.omdbapi.com/"

class OMDbService {

    fun getService(): OMDbServiceInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(OMDbServiceInterface::class.java)
    }
}