package com.example.inputdata

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface InformationDao {
    //ข้อมูลที่เก็บบันทึก
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInformation(IFM: Information)

    @Query("SELECT * FROM infor WHERE userId = :mID")
    fun getDATA(mID: Int):LiveData<Information>

    @Query("SELECT MAX(userId) FROM infor")
    fun getinforid(): Int

}