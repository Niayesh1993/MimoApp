package com.example.mimosampleapp.service.caller

import com.example.mimosampleapp.model.input.ApiResultModel
import retrofit2.Call
import retrofit2.http.GET

interface UserCaller {

    @GET("api/lessons")
    abstract fun getLesson(): Call<ApiResultModel>

}