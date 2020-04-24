package com.example.mimosampleapp.model.input

import android.os.Parcel
import android.os.Parcelable
import com.example.mimosampleapp.model.Lesson
import java.io.Serializable

class Course() : Serializable, Parcelable {

    var Course: List<Lesson>? = null

    constructor(parcel: Parcel) : this() {

    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {

        if (dest != null) {
            dest.writeParcelable(this.Course)

        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Course> {
        override fun createFromParcel(parcel: Parcel): Course {
            return Course(parcel)
        }

        override fun newArray(size: Int): Array<Course?> {
            return arrayOfNulls(size)
        }
    }


}

private fun Parcel?.writeParcelable(course: List<Lesson>?) {

}
