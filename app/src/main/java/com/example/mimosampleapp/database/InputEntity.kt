package com.example.mimosampleapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
class InputEntity {

    @PrimaryKey
    var ID: Int = 0

    @ColumnInfo
    var lnputId: Int = 0

    @ColumnInfo(name ="startIndex")
    var startIndex:  Int = 0

    @ColumnInfo(name ="endtIndex")
    var endIndex:  Int = 0


}