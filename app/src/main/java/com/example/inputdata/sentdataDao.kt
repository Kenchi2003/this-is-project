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

    @Query("SELECT date FROM Sendata")
    fun getDATE(): List<String>

    @Query("SELECT Id FROM Sendata")
    fun getID(): Int

    @Query("SELECT date FROM Sendata WHERE Id =:mid")
    fun getDateforID(mid: Int): String

    @Query("SELECT information FROM Sendata WHERE Id =:mid")
    fun getInformation(mid: Int): String

    @Query("SELECT MAX(Id) FROM Sendata")
    fun getsentid(): Int
}