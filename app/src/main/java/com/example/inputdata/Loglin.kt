package com.example.inputdata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Loglin : AppCompatActivity() {
    private lateinit var db: AppDatabase

    private lateinit var mUser: EditText
    private lateinit var mPassword: EditText
    private lateinit var mWarning: TextView

    private lateinit var BTLog: Button
//
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loglin)

        mUser = findViewById(R.id.Name)
        mPassword = findViewById(R.id.Password)
        mWarning = findViewById(R.id.warn)

        BTLog = findViewById(R.id.logs)
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "User.db"
        ).build()
        GlobalScope.launch {
            val contact = User(0, "John", "1","1")
            db.userDAO().insertUser(contact)
        }

        BTLog.setOnClickListener {
            val User = mUser.text.toString()
            val Passwors = mPassword.text.toString()
            GlobalScope.launch(Dispatchers.IO) {
                val check = db.userDAO().checkLogin(User, Passwors)

                withContext(Dispatchers.Main) {
                    if (check) {
                        Toast.makeText(this@Loglin, "Welcome", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@Loglin,MainActivity::class.java)
                        val select1 = User
                        intent.putExtra("MyKey1",select1)
                        startActivity(intent)
                        finish()
                    } else {
                        mWarning.text = "ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง"
                    }
                }
            }
        }
    }
}