package com.example.inputdata


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.icu.text.SimpleDateFormat
import android.icu.util.BuddhistCalendar
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZoneId
import java.time.chrono.ThaiBuddhistDate
import java.time.format.DateTimeFormatter
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var appdata: AppDatabase


    private lateinit var INum: EditText
    private lateinit var Smok: EditText
    private lateinit var Result1: TextView
    private lateinit var Result2: TextView
    private lateinit var viewDate: TextView
    private lateinit var Camera: ImageButton
    private lateinit var Sendinformation: ImageButton
    private lateinit var plusdata: Button
    private lateinit var mRadioGroup: RadioGroup
    private lateinit var spinpower: Spinner
    private lateinit var spinHR: Spinner
    private lateinit var spinMin: Spinner
    private lateinit var spinsec: Spinner
    private lateinit var spinvalue: Spinner
    private lateinit var mTextshow: TextView


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat", "WeekBasedYear", "SetTextI18n", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        INum = findViewById(R.id.Iwalther)
        Smok = findViewById(R.id.putSmoke)
        Result1 = findViewById(R.id.outresalt1)
        Result2 = findViewById(R.id.outresalt2)
        viewDate = findViewById(R.id.ViewDate)
        spinpower = findViewById(R.id.dropPower)
        spinHR = findViewById(R.id.TikHR)
        spinMin = findViewById(R.id.MinTik)
        spinsec = findViewById(R.id.SecTik)
        spinvalue = findViewById(R.id.ValueTik)
        mRadioGroup = findViewById(R.id.RGSmoke)
        plusdata = findViewById(R.id.PutData)
        Sendinformation = findViewById(R.id.Sentdata)

        Camera = findViewById(R.id.IconCam)


        mTextshow = findViewById(R.id.Showdata)
        mRadioGroup.setOnCheckedChangeListener{ group,checkedId ->
            Smok.isEnabled = checkedId != R.id.radioNoSmoke
        }

        //วันที่
        val tbd: ThaiBuddhistDate = ThaiBuddhistDate.now(ZoneId.systemDefault())
        val calendar = Calendar.getInstance()
        val dayFormat = SimpleDateFormat("dd")
        val day = dayFormat.format(calendar.time)
        val monthFormat = SimpleDateFormat("MMMM", Locale("th"))
        monthFormat.calendar = BuddhistCalendar()
        val month = monthFormat.format(calendar.time)
        val yearFormat = tbd.format(DateTimeFormatter.ofPattern("yyyy"))
        val year = yearFormat.format(calendar.time)

        viewDate = findViewById(R.id.ViewDate)
        viewDate.text = "วันที่ " + day.replace(" ", " ") +
                " เดือน " + month.replace(" "," ") +
                " พ.ศ. " + year.replace(" "," ")

        spin()
        spinHR()
        spinMin()
        spinSec()
        spinValue()

        Camera.setOnClickListener {
            openCamera()
        }

        plusdata.setOnClickListener {
            val Oldtext = mTextshow.text.toString()
            senInfor(Oldtext)

        }
        Sendinformation.setOnClickListener {
            showCurvedAlertDialog()
        }

    }
    private fun senInfor(OldText: String) {
        appdata = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "User.db"
        ).build()

        val mHR = spinHR.selectedItem.toString()
        val mMIN = spinMin.selectedItem.toString()
        val mSEC = spinsec.selectedItem.toString()
        val mValue = spinvalue.selectedItem.toString()

        CoroutineScope(Dispatchers.IO).launch {
            val infor = Information(0, mHR, mMIN, mSEC, mValue)
            appdata.informationDAO().insertInformation(infor)
            val ID = appdata.informationDAO().getinforid()
            val dataList: LiveData<Information> = appdata.informationDAO().getDATA(ID)

            withContext(Dispatchers.Main) {
                val stringBuilder = StringBuilder()
                dataList.observe(this@MainActivity, androidx.lifecycle.Observer { liveData ->
                    val information = liveData
                    val formattedText = String.format("%s : %s : %s = %s",
                        information.Hr,
                        information.Min,
                        information.Sec,
                        information.value)
                        val TextShow = "$OldText \n" + "$formattedText"
                        stringBuilder.append(TextShow)
                        mTextshow.text = stringBuilder.toString()
                })
            }
        }
    }

    private fun spin(){
        val item = listOf("เลือกประเภทเชื้อเพลิง","ใช้น้ำมันดีเซลเป็นเชื้อเพลิง","ใช้น้ำมันเตาเป็นเชื้อเพลิง","ใช้ถ่านหิน","ใช้เศษไม้ฟืนเป็นเชื้อเพลิง","อื่น ๆ")
        val adapter = ArrayAdapter(baseContext,
            android.R.layout.simple_spinner_dropdown_item,
            item)
        spinpower.adapter = adapter
    }
    private fun spinHR(){
        val items = MutableList(23) {"${it + 1}"}
        items.add(0, "0")
        val adapter = ArrayAdapter(baseContext,
            android.R.layout.simple_spinner_dropdown_item, items)
        spinHR.adapter = adapter
    }
    private fun spinMin(){
        val items = MutableList(59) {"${it + 1}"}
        items.add(0, "0")
        val adapter = ArrayAdapter(baseContext,
            android.R.layout.simple_spinner_dropdown_item, items)
        spinMin.adapter = adapter
    }
    private fun spinSec(){
        val items = MutableList(59) {"${it + 1}"}
        items.add(0, "0")
        val adapter = ArrayAdapter(baseContext,
            android.R.layout.simple_spinner_dropdown_item, items)
        spinsec.adapter = adapter
    }
    private fun spinValue(){
        val items = MutableList(59) {"${it + 1}"}
        items.add(0, "0")
        val adapter = ArrayAdapter(baseContext,
            android.R.layout.simple_spinner_dropdown_item, items)
        spinvalue.adapter = adapter
    }
    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivity(intent)
    }
    private fun showCurvedAlertDialog() {
        val  dialog : androidx.appcompat.app.AlertDialog = MaterialAlertDialogBuilder(this, R.style.RoundedMaterialDialog)
            .setView(R.layout.dialog)
            .show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        // ปิด Dialog
        dialog.findViewById<View>(R.id.button_close)!!.setOnClickListener {
            val intent = Intent(this@MainActivity,HistoyActivity::class.java)
            startActivity(intent)
        }
    }
}
