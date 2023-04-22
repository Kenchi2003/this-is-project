package com.example.inputdata

import android.os.Bundle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.inputdata.Adapter
import com.example.inputdata.AppDatabase
import com.example.inputdata.R

class HistoyActivity : AppCompatActivity() {
    private lateinit var appdata: AppDatabase
    private lateinit var recyclerView : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_histoy)
        recyclerView = findViewById(R.id.recyclerView_record)
        appdata = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "User.db"
        ).build()

        GlobalScope.launch(Dispatchers.IO) {
            val date: List<String> = appdata.sentdataDAO().getDATE()
            val ID = appdata.sentdataDAO().getID()
            val dateID = appdata.sentdataDAO().getDateforID(ID)
            val getInformationForID = appdata.sentdataDAO().getInformationforID(ID)
            launch(Dispatchers.Main) {
                recyclerView.layoutManager = LinearLayoutManager(this@HistoyActivity)
                val  itemAdapter = Adapter(date,this@HistoyActivity,ID,dateID,getInformationForID)
                recyclerView.adapter = itemAdapter
            }
        }
    }
}
