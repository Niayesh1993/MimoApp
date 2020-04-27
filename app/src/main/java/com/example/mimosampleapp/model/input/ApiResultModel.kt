package com.example.mimosampleapp.model.input

import com.example.mimosampleapp.model.Lesson
import com.google.gson.annotations.SerializedName

class ApiResultModel {

    @SerializedName("lessons")
    var data: List<Lesson>? = null

    var errors: String? = null
}