package com.example.inputdata

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    var userId: Int,
    val name: String,
    val user: String,
    val password: String
)
@Entity(tableName = "Sendata")
data class sentdata(
    @PrimaryKey(autoGenerate = true)
    var Id: Int,
    val inum: String,//ไอน้ำ
    val fuel: String,//เชื้อเพลิง
    val CtrlSmoke: String,//ระบบควบคุมเข่ม่าควัน
    val date: String,//วันที่
    val information: String,
    val resultopacity: String,//ผลรวมความทึบแสง
    val numsavetime: String,//จำนวนครั้งที่จดบันทึก
    val image: String
)