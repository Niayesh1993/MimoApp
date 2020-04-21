package com.example.mimosampleapp.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LessonContent {

    @SerializedName("color")
    @Expose
    var color: String? = null

    @SerializedName("text")
    @Expose
    var text: String? = null
}