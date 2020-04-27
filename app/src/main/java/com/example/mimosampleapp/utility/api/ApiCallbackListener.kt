package com.example.mimosampleapp.utility.api

import com.example.mimosampleapp.model.input.ApiResultModel

interface ApiCallbackListener {

    fun onSucceed(data: ApiResultModel)
    fun onError(errors: String)
}