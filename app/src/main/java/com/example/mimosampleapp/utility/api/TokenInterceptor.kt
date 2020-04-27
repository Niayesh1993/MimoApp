package com.example.mimosampleapp.utility.api

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor (context: Context): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val original = chain.request()

        val request = original
            .newBuilder()
            .method(original.method(), original.body()).build()



        return chain.proceed(request)
    }

    private val mContext: Context = context
}