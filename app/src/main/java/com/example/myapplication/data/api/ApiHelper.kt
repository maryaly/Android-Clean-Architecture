package com.example.myapplication.data.api

import com.example.myapplication.data.model.CitiesAndFoods
import retrofit2.Response

interface ApiHelper {

    suspend fun getCitiesAndFoods(): Response<CitiesAndFoods>
}