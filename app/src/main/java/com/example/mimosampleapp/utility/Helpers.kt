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
            // There might be an error.
            if (response.code() >= 400 && response.code() < 600) {
                // Request and server errors.
                var errors = ""
                val t = object : TypeToken<ApiResultModel>() {

                }.type
                val result =
                    Helpers().ParseJson<ApiResultModel>(response.errorBody()!!.string(), t)
                // If it has it's own error message return it
                if (result != null && !result.errors?.isEmpty()!!) {
                    //errors.add(new Error(response.code(), result.getErrors().get(0).getMessage()));
                    errors = result.errors.toString()
                    callbackListener.onError(errors)
                    //FirebaseCrash.report(new Throwable(result.getErrors().get(0).getMessage()));
                    // Log.e("API Helpers", result.getErrors().get(0).getMessage());
                } else { // Make a generic error message (mostly for error 500)
                    //errors.add(new Error(response.code(), "خطای سرور"));
                    errors = "خطای سرور"
                    callbackListener.onError(errors)
                    //FirebaseCrash.report(new Throwable("Handle Response Error Empty Error"));
                    Log.e("API Helpers", "Handle Response Error Empty Error")
                }
            } else { // undefined error code >= 600
                if (response.body() != null && response.body()!!.errors != null)
                    callbackListener.onError(response.body()!!.errors.toString())
                // We have to return something, right?
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
        //errors.add(new NetworkError(context));
        errors = NetworkError(context).getMessage().toString()
        callbackListener.onError(errors)
    }
}