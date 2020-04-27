package com.example.mimosampleapp.utility.api

import android.content.Context
import android.util.Log
import com.example.mimosampleapp.service.user.UserService
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor (context: Context): Interceptor
{
    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()
        val response = chain.proceed(originalRequest)

        if (response.code() == 403) {
            val userService = UserService(context)

        }

        return response
    }

    private val context: Context = context
}