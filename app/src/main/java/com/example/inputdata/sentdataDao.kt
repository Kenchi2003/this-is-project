package com.example.inputdata

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface sentdataDao {

    //ข้อมูลโดยรวม
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertsentdata(data: sentdata)

    @Query("SELECT * FROM Sendata WHERE Id = :mID")
    fun getData(mID: Int): LiveData<sentdata>

    @Query("SELECT data FROM infor WHERE Id =:mid")
    fun getinforid(mID: Int): Int

}