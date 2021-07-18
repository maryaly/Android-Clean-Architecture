package com.example.myapplication.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class CitiesAndFoods(
    @SerializedName("foods")
    val mFoods: List<Food>?,
    @SerializedName("cities")
    val mCities: List<City>?
)

data class Food(
    @SerializedName("name")
    val mName: String?,
    @SerializedName("image")
    val mImage: String?
)

@Parcelize
data class City(
    @SerializedName("name")
    val mName: String?,
    @SerializedName("image")
    val mImage: String?,
    @SerializedName("description")
    val mDescription: String?
): Parcelable