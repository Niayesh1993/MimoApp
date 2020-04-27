package com.example.mimosampleapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mimosampleapp.database.caller.CmpDAO
import com.example.mimosampleapp.database.caller.ContentDAO
import com.example.mimosampleapp.database.caller.InputDAO
import com.example.mimosampleapp.database.caller.LessonDAO

@Database (entities = [(LessonEntity::class),(InputEntity::class),(ContentEntity::class),(CmpEntity::class)],version = 1)
abstract class AppDb : RoomDatabase() {

    abstract fun lessonDAO(): LessonDAO
    abstract fun InputDAO(): InputDAO
    abstract fun ContentDAO(): ContentDAO
    abstract fun CmpDAO(): CmpDAO



}