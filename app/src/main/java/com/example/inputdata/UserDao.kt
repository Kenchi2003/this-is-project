package com.example.inputdata

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface UserDao {

    //ข้อมูลผู้ใช้
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Update
    fun updateUser(user: User)

    @Query("SELECT * FROM users")
    fun getUsers(): List<User>

    @Query("SELECT * FROM users WHERE user = :mUser AND password =:mPassword")
    fun checkLogin(mUser: String, mPassword: String): Boolean

}