package com.example.inputdata

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inputdata.com.example.inputdata.Adapter

class HistoyActivity : AppCompatActivity() {
    private lateinit var recyclerView : RecyclerView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_histoy)
        recyclerView = findViewById(R.id.recyclerView_record)

        val DATETime = intent.getStringExtra("MyKey1")
        val date = DATETime.toString()
        
        recyclerView.layoutManager = LinearLayoutManager(this)
        val  itemAdapter = Adapter(date,this)

        recyclerView.adapter = itemAdapter
    }
}