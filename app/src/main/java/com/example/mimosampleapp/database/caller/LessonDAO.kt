package com.example.mimosampleapp.database.caller

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mimosampleapp.database.LessonEntity

@Dao
interface LessonDAO {

    @Insert
    fun saveLessons(book: LessonEntity)

    @Query(value = "Select * from LessonEntity")
    fun getAllLessons() : List<LessonEntity>


    @Query("UPDATE LessonEntity SET LessonStatus = :lstatus WHERE lessonId = :lid")
    fun updateLesson(lid: Int, lstatus: Boolean?): Int

    @Query("DELETE FROM LessonEntity")
    fun DeletLesson()

}