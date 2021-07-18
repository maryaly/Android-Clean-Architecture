package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.BuildConfig
import com.example.myapplication.data.api.ApiHelper
import com.example.myapplication.data.api.ApiHelperImpl
import com.example.myapplication.data.api.ApiService
import com.example.myapplication.util.Constants
import com.example.myapplication.util.ErrorAnalyzer
import com.example.myapplication.util.network.NetworkHelper
import com.example.myapplication.util.network.NetworkHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    @Singleton
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext mContext: Context
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG)
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        else
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE

        return OkHttpClient.Builder()
            .connectTimeout(Constants.THIRTY_SECONDS, TimeUnit.SECONDS)
            .readTimeout(Constants.THIRTY_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(Constants.THIRTY_SECONDS, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(loggingInterceptor)
            .build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(mOkHttpClient: OkHttpClient, BASE_URL: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(mOkHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideNetworkHelperImpl(@ApplicationContext mContext: Context): NetworkHelper =
        NetworkHelperImpl(mContext)

    @Provides
    @Singleton
    fun provideApiService(mRetrofit: Retrofit): ApiService =
        mRetrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(mApiHelper: ApiHelperImpl): ApiHelper = mApiHelper

    @Provides
    @Singleton
    fun provideErrorAnalyzer(@ApplicationContext mContext: Context): ErrorAnalyzer =
        ErrorAnalyzer(mContext)
}