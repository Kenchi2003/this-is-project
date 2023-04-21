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

//    @Query("SELECT MAX(Hr) FROM infor")
//    fun getinforHR(): Int
//
//    @Query("SELECT MAX(Min) FROM infor")
//    fun getinforMIN(): Int
//
//    @Query("SELECT MAX(Sec) FROM infor")
//    fun getinforSEC(): Int
//
//    @Query("SELECT MAX(value) FROM infor")
//    fun getinforVALUE(): Int


//    @Delete
//    fun deleteinfor(IFM: Information)
//
//    @Update
//    fun updateInfor(IFM: Information)
//

}