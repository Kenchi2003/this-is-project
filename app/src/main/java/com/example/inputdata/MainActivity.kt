package com.example.inputdata


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.icu.text.SimpleDateFormat
import android.icu.util.BuddhistCalendar
import android.icu.util.Calendar
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
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
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private var REQUEST_IMAGE_CAPTURE = 1000
    private val CAMERA_PERMISSION_REQUEST_CODE = 101

    private lateinit var Sendinformation: ImageButton
    private lateinit var plusdata: Button
    private lateinit var mRadioGroup: RadioGroup
    private lateinit var spinpower: Spinner
    private lateinit var spinHR: Spinner
    private lateinit var spinMin: Spinner
    private lateinit var spinsec: Spinner
    private lateinit var spinvalue: Spinner
    private lateinit var mTextshow: TextView
    private lateinit var editText: EditText

    private var IMAGE: String = ""

    private var SMOKE: String = ""

    @SuppressLint("SimpleDateFormat", "WeekBasedYear", "SetTextI18n", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appdata = AppDatabase.AppDatabaseSingleton.getInstance(applicationContext)


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
        editText = findViewById(R.id.editText)

        Camera = findViewById(R.id.IconCam)


        mTextshow = findViewById(R.id.ShowResalt)


        mRadioGroup.setOnCheckedChangeListener{ group,checkedId ->
            if (checkedId == R.id.radioNoSmoke){
                Smok.hint = "ไม่มีระบบควบคุมเขม่าควัน"
                Smok.isEnabled = false
                SMOKE = Smok.hint.toString()
            }else{
                SMOKE = Smok.text.toString()
            }
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

        val monthFormat1 = SimpleDateFormat("MM", Locale("th"))
        monthFormat1.calendar = BuddhistCalendar()
        val month1 = monthFormat1.format(calendar.time)


        val formatdate = day.replace(" ", " ") +
                "/" + month1.replace(" "," ") +
                "/" + year.replace(" "," ")


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
            val result = Result1.text.toString()
            senInfor(Oldtext,result)

        }
        Sendinformation.setOnClickListener {
            showCurvedAlertDialog(SMOKE,DATE1 = formatdate)
        }
    }
    private fun saveImage(imageBitmap: Bitmap): String {
        val file = File(
            getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "${System.currentTimeMillis()}.jpg"
        )
        val outputStream = FileOutputStream(file)
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        return file.absolutePath
    }
    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
    private fun senInfor(OldText: String,result: String) {


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
                val IntBuilder = StringBuilder()
                dataList.observe(this@MainActivity, androidx.lifecycle.Observer { liveData ->
                    val information = liveData
                    val formattedText = String.format("%s : %s : %s = %s",
                        information.Hr,
                        information.Min,
                        information.Sec,
                        information.value)
                    val TextShow = if (OldText.isEmpty()) {
                        formattedText
                    } else {
                        "$OldText \n$formattedText"
                    }
                    stringBuilder.append(TextShow)
                    mTextshow.text = stringBuilder.toString()
                    val intValue = mValue.toIntOrNull() ?: 0
                    val resultValue = result.toIntOrNull() ?: 0
                    val textResult = intValue + resultValue
                    IntBuilder.append(textResult)
                    Result1.text = IntBuilder.toString()
                })
            }
        }
    }

    private fun spin(){
        val item = listOf("เลือกประเภทเชื้อเพลิง",
            "ใช้น้ำมันดีเซลเป็นเชื้อเพลิง",
            "ใช้น้ำมันเตาเป็นเชื้อเพลิง",
            "ใช้ถ่านหิน",
            "ใช้เศษไม้ฟืนเป็นเชื้อเพลิง",
            "ใช้กะลาปาล์มเป็นเชื้อเพลิง",
            "ใช้กะลามะพร้าวเป็นเชื้อเพลิง",
            "อื่น ๆ")
        spinpower.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(parent?.getItemAtPosition(position).toString() == "อื่น ๆ"){
                    editText.visibility = View.VISIBLE //แสดง EditText เมื่อเลือก "อื่น ๆ"
                }else{
                    editText.visibility = View.GONE //ซ่อน EditText เมื่อไม่ได้เลือก "อื่น ๆ"
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
        }
    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            CoroutineScope(Dispatchers.IO).launch {
                val imageBitmap = data?.extras?.get("data") as Bitmap
                val imagePath = saveImage(imageBitmap)
                IMAGE = imagePath
            }
        }
    }
    private fun showCurvedAlertDialog(SMOKE:String,DATE1:String) {
        appdata = AppDatabase.AppDatabaseSingleton.getInstance(applicationContext)
        val Inum = INum.toString()
        val Power = spinpower.selectedItem.toString()
        val SMOKE = SMOKE
        val DATE = DATE1
        val INFOR = mTextshow.text.toString()
        val result1 = Result1.text.toString()
        val result2 = Result2.text.toString()
        val Image = IMAGE
        CoroutineScope(Dispatchers.IO).launch {
            val infor = sentdata(0, Inum, Power, SMOKE, DATE, INFOR, result1, result2, Image)
            appdata.sentdataDAO().insertsentdata(infor)

            // สร้าง Handler บน Main Thread เพื่อปิด Dialog
            runOnUiThread {
                val dialog: androidx.appcompat.app.AlertDialog =
                    MaterialAlertDialogBuilder(this@MainActivity, R.style.RoundedMaterialDialog)
                        .setView(R.layout.dialog)
                        .show()
                dialog.window?.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

                dialog.findViewById<View>(R.id.button_close)!!.setOnClickListener {

                    val intent = Intent(this@MainActivity, HistoyActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}
