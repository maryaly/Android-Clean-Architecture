package com.example.myapplication.util

import android.content.Context
import android.util.MalformedJsonException
import com.example.myapplication.R
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import java.io.EOFException
import java.io.IOException
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import com.example.myapplication.BuildConfig


open class ErrorAnalyzer (private val context: Context) {

    fun getAnalyzedErrorMessage(t: Throwable): String {
        return when {
            (t is JsonSyntaxException) or (t is MalformedJsonException) -> {
                if (BuildConfig.DEBUG)
                    t.printStackTrace()
                context.getString(R.string.wrong_answer_from_server)
            }
            t.cause is SocketTimeoutException -> context.getString(R.string.no_response)
            t is EOFException -> context.getString(R.string.empty_error)
            t.cause is UnknownHostException -> context.getString(R.string.error_connecting_to_server)
            t.cause is ConnectException -> context.getString(R.string.error_connecting_to_server)
            t is IOException -> context.getString(R.string.error_check_Internet_connection)
            t is HttpException -> run {
                if (t.code() == HttpURLConnection.HTTP_UNAUTHORIZED)
                    context.getString(R.string.authorization_error)
                else {
                    val responseBody = t.response()?.errorBody()
                    if (responseBody != null) {
                        val error = responseBody.string()
                        if (error.isNotEmpty())
                            error
                        else
                            context.getString(R.string.http_error_code, t.response()?.code())
                    } else
                        context.getString(R.string.http_error_code, t.response()?.code())
                }
            }
            else -> t.message ?: "Error"
        }
    }
}