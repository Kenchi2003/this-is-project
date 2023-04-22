package com.example.inputdata

import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import kotlinx.coroutines.CoroutineScope

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
            val ID = appdata.sentdataDAO().getID()
            val date: List<String> = appdata.sentdataDAO().getDateforID(ID)
            val information = appdata.sentdataDAO().getInformation(ID)
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(this@HistoyActivity, getInformationForID, Toast.LENGTH_SHORT).show()
            }
            launch(Dispatchers.Main) {
                recyclerView.layoutManager = LinearLayoutManager(this@HistoyActivity)
                val  itemAdapter = Adapter(date,this@HistoyActivity,ID,dateID,getInformationForID)
                recyclerView.adapter = itemAdapter
            }
        }
    }
}
