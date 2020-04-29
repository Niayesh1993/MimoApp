package com.example.mimosampleapp.utility

import com.google.gson.annotations.SerializedName

open class Error (code: Int, message: String?) {

    @SerializedName("Code")
    private var code: Int = 0
    @SerializedName("Message")
    private var message: String? = null

    init {

        this.code = code
        this.message = message
    }

    fun getMessage(): String? {
        return message
    }

}