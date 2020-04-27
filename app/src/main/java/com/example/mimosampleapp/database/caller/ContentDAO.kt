package com.example.mimosampleapp.database.caller

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mimosampleapp.database.ContentEntity
import com.example.mimosampleapp.model.LessonContent

@Dao
interface ContentDAO {

    @Insert
    fun saveContent(ContentEntity: ContentEntity)

    @Query(value = "Select * from ContentEntity")
    fun getAllcontent() : List<ContentEntity>

    @Query("Select * from ContentEntity WHERE ContentId =:id")
    fun selectContent(id: Int): List<LessonContent>

    @Query("DELETE FROM ContentEntity")
    fun DeletContent()

}