package com.example.inputdata


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.inputdata.databinding.ActivityLoglinBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Loglin : AppCompatActivity() {
    private lateinit var db: AppDatabase

    private lateinit var binding: ActivityLoglinBinding

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }
    @SuppressLint("MissingInflatedId", "UseSwitchCompatOrMaterialCode", "CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoglinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.AppDatabaseSingleton.getInstance(applicationContext)

        val OldUser = getSharedPreferences("MyP", MODE_PRIVATE)
        val editter = OldUser.edit()
        binding.apply {
            val mOldUser = OldUser.getString("user", null).toString()
            val mOldPassword = OldUser.getString("pass", null).toString()
            GlobalScope.launch(Dispatchers.IO) {
                val check = db.userDAO().checkLogin(mOldUser, mOldPassword)
                withContext(Dispatchers.Main) {
                    if (check) {
                        val intent = Intent(this@Loglin,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }

        val mUser = findViewById<EditText>(R.id.Name)
        val mPassword = findViewById<EditText>(R.id.Password)
        val mWarning = findViewById<TextView>(R.id.warn)

        val BTLog = findViewById<Button>(R.id.logs)

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
                        editter.apply{
                            putString("user", User)
                            putString("pass", Passwors)
                            apply()
                        }
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