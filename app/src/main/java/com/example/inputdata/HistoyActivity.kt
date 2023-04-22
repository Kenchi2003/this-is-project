package com.example.inputdata

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room

class HistoyActivity : AppCompatActivity() {
    private lateinit var appdata: AppDatabase
    private lateinit var recyclerView : RecyclerView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_histoy)
        recyclerView = findViewById(R.id.recyclerView_record)
        appdata = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "User.db"
        ).build()
        val date: List<String> = appdata.sentdataDAO().getDATE()
        
        recyclerView.layoutManager = LinearLayoutManager(this)
        val  itemAdapter = Adapter(date,this@HistoyActivity)

        recyclerView.adapter = itemAdapter
    }
}