package com.example.mimosampleapp.utility.api

import android.content.Context
import com.example.mimosampleapp.utility.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitManager(context: Context, url: String, includeToken: Boolean)
{

    private var retrofit: Retrofit? = null
    private var retrofitWithToken: Retrofit? = null
    private var retrofitGoogle: Retrofit? = null
    private val context: Context = context
    private val url: String = url
    private val includeToken: Boolean = includeToken

    fun getRetrofitInstance(): Retrofit? {
        val httpClient = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        httpClient.addInterceptor(loggingInterceptor)
        httpClient.connectTimeout(1, TimeUnit.MINUTES)
        httpClient.readTimeout(1, TimeUnit.MINUTES)
        httpClient.writeTimeout(1, TimeUnit.MINUTES)
        var client = httpClient.build()

        when (url) {
            Constants().URL_BASE -> if (includeToken) {
                if (retrofitWithToken == null) {
                    httpClient.addInterceptor(TokenInterceptor(context))
                    httpClient.addInterceptor(AuthenticationInterceptor(context))
                    client = httpClient.build()
                    retrofitWithToken = Retrofit.Builder()
                        .baseUrl(url)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
                return retrofitWithToken
            } else {
                if (retrofit == null) {
                    httpClient.addInterceptor(AuthenticationInterceptor(context))
                    client = httpClient.build()
                    retrofit = Retrofit.Builder()
                        .baseUrl(url)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create()).build()
                }
                return retrofit
            }
            Constants().URL_GOOGLE -> {
                if (retrofitGoogle == null) {
                    retrofitGoogle = Retrofit.Builder()
                        .baseUrl(url)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
                return retrofitGoogle
            }
            else -> if (includeToken) {
                if (retrofitWithToken == null) {
                    httpClient.addInterceptor(TokenInterceptor(context))
                    httpClient.addInterceptor(AuthenticationInterceptor(context))
                    client = httpClient.build()
                    retrofitWithToken =
                        Retrofit.Builder().baseUrl(url).client(client).addConverterFactory(
                            GsonConverterFactory.create()
                        ).build()
                }
                return retrofitWithToken
            } else {
                if (retrofit == null) {
                    httpClient.addInterceptor(AuthenticationInterceptor(context))
                    client = httpClient.build()
                    retrofit = Retrofit.Builder().baseUrl(url).client(client)
                        .addConverterFactory(GsonConverterFactory.create()).build()
                }
                return retrofit
            }
        }
    }
}

