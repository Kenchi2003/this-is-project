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
            val date: List<String> = appdata.sentdataDAO().getDateforID()
            val information: List<String> = appdata.sentdataDAO().getInformation()
            val image: List<String> = appdata.sentdataDAO().getImage()
            val resultopacity: List<String> = appdata.sentdataDAO().getresultopacity()
            val numsavetime: List<String> = appdata.sentdataDAO().getnumsavetime()

            launch(Dispatchers.Main) {
                recyclerView.layoutManager = LinearLayoutManager(this@HistoyActivity)
                val  itemAdapter = Adapter(date,this@HistoyActivity,information,resultopacity,numsavetime,image)
                recyclerView.adapter = itemAdapter
            }
        }
    }
}
