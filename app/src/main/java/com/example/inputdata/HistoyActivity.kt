package com.example.inputdata

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inputdata.com.example.inputdata.Adapter

class HistoyActivity : AppCompatActivity() {
    private var date = arrayOf(
        "21/03/2566",
        "22/03/2566",
        "23/03/2566",
        "24/03/2566",
        "25/04/2566",
        "26/03/2566",
        "27/03/2566",
        "28/03/2655",
        "29/03/2546",
        "30/03/2500"
    )
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