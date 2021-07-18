package com.example.myapplication.data.repository

import com.example.myapplication.data.api.ApiHelper
import com.example.myapplication.data.model.CitiesAndFoods
import retrofit2.Response
import javax.inject.Inject

class CitiesAndFoodsRepository @Inject constructor(
    private val mApiHelper: ApiHelper,
) {

    suspend fun getCitiesAndFoods(): Response<CitiesAndFoods> =
        mApiHelper.getCitiesAndFoods()
}