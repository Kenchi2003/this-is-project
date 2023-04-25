package com.example.inputdata

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HistoyActivity : AppCompatActivity() {
    private lateinit var appdata: AppDatabase
    private lateinit var recyclerView : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_histoy)
        recyclerView = findViewById(R.id.recyclerView_record)
        appdata = AppDatabase.AppDatabaseSingleton.getInstance(applicationContext)

        GlobalScope.launch(Dispatchers.IO) {
            val ID = appdata.sentdataDAO().getID()
            val date: List<String> = appdata.sentdataDAO().getDateforID()
            val DateID = appdata.sentdataDAO().getDateID(ID)
            val information: List<String> = appdata.sentdataDAO().getInformation()

            launch(Dispatchers.Main) {
                recyclerView.layoutManager = LinearLayoutManager(this@HistoyActivity)
                val  itemAdapter = Adapter(date,this@HistoyActivity,DateID,information)
                recyclerView.adapter = itemAdapter
            }
        }
    }
}
