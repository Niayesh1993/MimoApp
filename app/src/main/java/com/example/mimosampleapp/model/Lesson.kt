package com.example.mimosampleapp.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Lesson() : Serializable {

    @SerializedName("id")
    @Expose
    var lessonId: Int? = null

    @SerializedName("content")
    @Expose
    var contents: List<LessonContent>? = null

    @SerializedName("input")
    var input: LessonInput? = null

    var pass_status: Boolean = false



    data class Object (
        var lessonId: Int? = null,
        var contents: LessonContent? = null,
        var input: LessonInput? = null,
        var pass_status: Boolean? = null

    ) : Serializable



}



