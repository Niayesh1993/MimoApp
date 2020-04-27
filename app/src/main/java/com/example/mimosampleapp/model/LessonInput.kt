package com.example.mimosampleapp.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class LessonInput : Serializable {

    @SerializedName("startIndex")
    @Expose
    var startIndex: Int? = null

    @SerializedName("endIndex")
    @Expose
    var endIndex: Int? = null


}