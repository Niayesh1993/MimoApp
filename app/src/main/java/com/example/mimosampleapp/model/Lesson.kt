package com.example.mimosampleapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Lesson {

    @SerializedName("id")
    @Expose
    var lessonId: Int? = null

    @SerializedName("content")
    @Expose
    var contents: List<LessonContent>? = null

    @SerializedName("input")
    @Expose
    var input: LessonInput? = null



}