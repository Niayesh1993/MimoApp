package com.example.mimosampleapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ContentEntity {

    @PrimaryKey(autoGenerate = true)
    var ID: Int = 0

    @ColumnInfo
    var ContentId: Int = 0

    @ColumnInfo(name ="color")
    var color:  String = ""

    @ColumnInfo(name ="text")
    var text:  String = ""
}