package com.example.mimosampleapp.database.caller

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mimosampleapp.database.CmpEntity

@Dao
interface CmpDAO{

    @Insert
    fun saveCmp(CmpEntity: CmpEntity)

    @Query(value = "Select * from CmpEntity")
    fun getAllCmp() : List<CmpEntity>

    @Query("DELETE FROM CmpEntity")
    fun DeletCmp()

}