package com.example.mimosampleapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mimosampleapp.model.LessonContent
import com.example.mimosampleapp.model.LessonInput

@Entity
class LessonEntity {

    @PrimaryKey
    var lessonId: Int = 0

//    @ColumnInfo (name ="LessonContent")
//    var LessonContent:  List<LessonContent>? = null

//    @ColumnInfo (name ="LessonIndex")
//    var LessonIndex:  LessonInput = LessonInput()

    @ColumnInfo (name ="LessonStatus")
    var LessonStatus:  Boolean = false
}