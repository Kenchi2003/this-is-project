package com.example.inputdata

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
class Adapter(
    private val item: List<String>,
    private val context: Context,
    private val dataForID: String,
    private val inforID: List<String>,
    private val result: String,
    private val result1: String
) : RecyclerView.Adapter<Adapter.VHolder>() {

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_record, parent, false)
        return VHolder(v)
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.textDate.text = item[position]
        holder.imageView.setOnClickListener {
            showCurvedAlertDialog(position)
        }
    }

    inner class VHolder(v: View) : RecyclerView.ViewHolder(v) {
        val textDate: TextView = v.findViewById(R.id.textView_date)
        val imageView: AppCompatImageButton = v.findViewById(R.id.imageButton)
    }

    private fun showCurvedAlertDialog(position: Int) {
        inforID.size
        val dialog: androidx.appcompat.app.AlertDialog? = MaterialAlertDialogBuilder(context, R.style.RoundedMaterialDialog)
            .setView(R.layout.fragment_dialog)
            .show()

        if (dialog != null) {
            dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            
        }

        // set data to views in dialog
        val messageTextView = dialog?.findViewById<TextView>(R.id.TextDate)
        messageTextView?.text = "วันที่ ${item[position]}"

        val show = dialog?.findViewById<TextView>(R.id.ShowResalt)!!
        show?.text = "${inforID[position]}"

        val value = dialog?.findViewById<TextView>(R.id.TextShowResalt1)
        value?.text = "${result[position]}"

        val value1 = dialog?.findViewById<TextView>(R.id.TextShowResalt2)
        value1?.text = "${result1[position]}"

        dialog.findViewById<View>(R.id.button_close)?.setOnClickListener {
            dialog.dismiss()
        }
    }
}
