package com.example.mimosampleapp.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Lesson() : Serializable, Parcelable {

    @SerializedName("id")
    @Expose
    var lessonId: Int? = null

    @SerializedName("content")
    @Expose
    var contents: List<LessonContent>? = null

    @SerializedName("input")
    var input: LessonInput? = null

    var pass_status: Boolean = false

    constructor(parcel: Parcel) : this() {
        lessonId = parcel.readValue(Int::class.java.classLoader) as? Int
        pass_status = parcel.readByte() != 0.toByte()
    }


    data class Object (
        var lessonId: Int? = null,
        var contents: LessonContent? = null,
        var input: LessonInput? = null,
        var pass_status: Boolean? = null

    ) : Serializable

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("Not yet implemented")
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<Lesson> {
        override fun createFromParcel(parcel: Parcel): Lesson {
            return Lesson(parcel)
        }

        override fun newArray(size: Int): Array<Lesson?> {
            return arrayOfNulls(size)
        }
    }


}



