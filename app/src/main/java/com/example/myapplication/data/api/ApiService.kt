package com.example.myapplication.data.api

import com.example.myapplication.data.model.CitiesAndFoods
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("a2b63ef226c08553b2f9")
    suspend fun getCitiesAndFoods(): Response<CitiesAndFoods>
}