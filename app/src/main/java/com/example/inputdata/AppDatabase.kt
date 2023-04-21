package com.example.inputdata



import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [User::class , Information::class , sentdata::class], version = 1)
abstract class AppDatabase(): RoomDatabase() {
    abstract fun userDAO(): UserDao
    abstract fun informationDAO(): InformationDao
    abstract fun sentdataDAO(): sentdataDao
}