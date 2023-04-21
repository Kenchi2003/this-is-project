//package com.example.inputdata
//
//import android.content.Context
//import android.database.Cursor
//import androidx.lifecycle.ViewModel
//import androidx.room.Room
//
//
//class DataViewModel : ViewModel(){
//    private lateinit var db:AppDatabase
//
//    lateinit var adapter: Adapter
//    lateinit var cursor: Cursor
//
//    fun FetchData(context: Context){
//        db = Room.databaseBuilder(context)
//        cursor = db.userDAO().getAll()
//    }
//}