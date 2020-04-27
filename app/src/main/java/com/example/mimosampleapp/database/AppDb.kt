package com.example.mimosampleapp.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database (entities = [(LessonEntity::class),(InputEntity::class),(ContentEntity::class)],version = 1)
abstract class AppDb : RoomDatabase() {

    abstract fun lessonDAO(): LessonDAO
    abstract fun InputDAO(): InputDAO
    abstract fun ContentDAO(): ContentDAO

}