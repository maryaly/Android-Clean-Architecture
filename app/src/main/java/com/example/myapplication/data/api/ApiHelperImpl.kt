package com.example.myapplication.data.api

import com.example.myapplication.data.model.CitiesAndFoods
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val mApiService: ApiService) : ApiHelper {

    override suspend fun getCitiesAndFoods(): Response<CitiesAndFoods> =
        mApiService.getCitiesAndFoods()
}