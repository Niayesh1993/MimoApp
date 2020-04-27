package com.example.mimosampleapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mimosampleapp.model.Lesson

@Dao
interface LessonDAO {

    @Insert
    fun saveLessons(book: LessonEntity)

    @Query(value = "Select * from LessonEntity")
    fun getAllLessons() : List<LessonEntity>

//    @Query("Select * from LessonEntity WHERE lessonId =:id")
//    fun selectLesson(id: Int): Lesson?

    @Query("UPDATE LessonEntity SET LessonStatus = :lstatus WHERE lessonId = :lid")
    fun updateLesson(lid: Int, lstatus: Boolean?): Int

}