package com.example.mimosampleapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LessonInput {

    @SerializedName("startIndex")
    @Expose
    var startIndex: Int? = null

    @SerializedName("endIndex")
    @Expose
    var endIndex: Int? = null
}