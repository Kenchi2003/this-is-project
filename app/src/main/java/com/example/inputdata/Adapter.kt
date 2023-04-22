package com.example.inputdata

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class Adapter(private val item: List<String>,
              private val context: Context ,
              private val dataForID:String,
              private val inforID:String) : RecyclerView.Adapter<Adapter.VHolder>() {
    override fun getItemCount(): Int {
        return item.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_record, parent, false)
        val holder = VHolder(v)
        holder.imageView.setOnClickListener {
            showCurvedAlertDialog()
        }
        return holder
    }

    override fun onBindViewHolder(holder: VHolder, position:Int) {
        holder.textDate.text = item[position]

    }
    class VHolder(v: View) : RecyclerView.ViewHolder(v){
        val textDate : TextView = v.findViewById(R.id.textView_date)
        val imageView : AppCompatImageButton = v.findViewById(R.id.imageButton)
    }
    //ฟังก์ชัน Dialog
    private fun showCurvedAlertDialog() {
        val  dialog : androidx.appcompat.app.AlertDialog = MaterialAlertDialogBuilder(context,
            R.style.RoundedMaterialDialog
        )
            .setView(R.layout.fragment_dialog)
            .show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        // RecyclerView ใน Dialog
        val show: TextView = dialog.findViewById(R.id.ShowResalt)!!
        val SHOW = show.text.toString()
        // ผลรวมความทึบเเสงที่อ่านได้ ใน Dialog
        val messageTextView = dialog.findViewById<TextView>(R.id.TextDate)
        if (messageTextView != null) {
            messageTextView.text = "วันที่ $dataForID"
        }
        // ปิด Dialog
        dialog.findViewById<View>(R.id.button_close)!!.setOnClickListener {
            dialog.dismiss()
        }
    }
}