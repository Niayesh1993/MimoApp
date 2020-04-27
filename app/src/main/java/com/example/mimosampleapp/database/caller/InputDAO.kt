package com.example.mimosampleapp.database.caller

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mimosampleapp.database.InputEntity
import com.example.mimosampleapp.model.LessonInput

@Dao
interface InputDAO {

    @Insert
    fun saveInputs(book: InputEntity)

    @Query(value = "Select * from InputEntity")
    fun getAllInputs() : List<InputEntity>

    @Query("Select * from InputEntity WHERE lnputId =:id")
    fun selectInput(id: Int): LessonInput?

    @Query("DELETE FROM InputEntity")
    fun DeletInput()
}