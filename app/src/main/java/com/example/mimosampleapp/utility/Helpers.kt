package com.example.mimosampleapp.utility

import android.content.Context
import android.util.Log
import com.example.mimosampleapp.model.input.ApiResultModel
import com.example.mimosampleapp.utility.api.ApiCallbackListener
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import retrofit2.Response
import java.io.IOException
import java.lang.reflect.Type

class Helpers {

    fun <T> ParseJson(jsonString: String, t: Type): T? {
        val builder = GsonBuilder()
        val gson = builder.create()
        return gson.fromJson<T>(jsonString, t)
    }

    @Throws(IOException::class)
    fun  HandleResponse(
        response: Response<ApiResultModel>,
        callbackListener: ApiCallbackListener
    ) {
        if (response.code() >= 200 && response.code() < 300) {
            response.body()?.let { callbackListener.onSucceed(it) }
        } else {
            if (response.code() >= 400 && response.code() < 600) {
                var errors = ""
                val t = object : TypeToken<ApiResultModel>() {

                }.type
                val result =
                    Helpers().ParseJson<ApiResultModel>(response.errorBody()!!.string(), t)
                if (result != null && !result.errors?.isEmpty()!!) {
                    errors = result.errors.toString()
                    callbackListener.onError(errors)

                } else {
                    errors = "خطای سرور"
                    callbackListener.onError(errors)
                    Log.e("API Helpers", "Handle Response Error Empty Error")
                }
            } else {
                if (response.body() != null && response.body()!!.errors != null)
                    callbackListener.onError(response.body()!!.errors.toString())
                callbackListener.onError("")
            }
        }
    }

    fun HandleErrors(
        t: Throwable,
        context: Context,
        callbackListener: ApiCallbackListener
    ) {
        Log.e("API Helpers", "onFailure: ", t)
        var errors = ""
        errors = NetworkError(context).getMessage().toString()
        callbackListener.onError(errors)
    }
}