package com.example.mimosampleapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class CmpEntity {

    @PrimaryKey(autoGenerate = true)
    var ID: Int = 0

    @ColumnInfo
    var CmpId: Int = 0

    @ColumnInfo(name ="start")
    var start:  Long = 0

    @ColumnInfo(name ="end")
    var end:  Long = 0
}