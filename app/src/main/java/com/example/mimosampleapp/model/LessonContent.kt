package com.example.mimosampleapp.model


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class LessonContent : Serializable {

    @SerializedName("color")
    @Expose
    var color: String? = null

    @SerializedName("text")
    @Expose
    var text: String? = null


}