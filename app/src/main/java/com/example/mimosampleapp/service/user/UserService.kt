package com.example.mimosampleapp.service.user

import android.content.Context
import com.example.mimosampleapp.model.input.ApiResultModel
import com.example.mimosampleapp.service.caller.UserCaller
import com.example.mimosampleapp.utility.Constants
import com.example.mimosampleapp.utility.Helpers
import com.example.mimosampleapp.utility.api.ApiCallbackListener
import com.example.mimosampleapp.utility.api.RetrofitManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class UserService(context: Context) {

    private val retrofit: Retrofit
    private val caller: UserCaller
    private val context: Context = context

    init {
        this.retrofit = RetrofitManager(
            context,
            Constants().URL_BASE
        ).getRetrofitInstance()!!
        caller = retrofit.create(UserCaller::class.java)

    }

    fun GetLessons(apiCallbackListener: ApiCallbackListener)
    {
        try {
            val call = caller.getLesson()
            call.enqueue(object : Callback<ApiResultModel> {

                override fun onResponse(
                    call: Call<ApiResultModel>,
                    response: Response<ApiResultModel>
                ) {
                    try {
                        Helpers().HandleResponse(response, apiCallbackListener)
                    } catch (e: Exception) {
                        apiCallbackListener.onError("")
                    }
                }
                override fun onFailure(call: Call<ApiResultModel>, t: Throwable) {
                    Helpers().HandleErrors(t, context, apiCallbackListener)
                }

            })
        } catch (e: Exception) {

            e.message
        }


    }

}