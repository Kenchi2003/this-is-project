package com.example.inputdata

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.bumptech.glide.Glide


class Adapter(
    private val item: List<String>,
    private val context: Context,
    private val inforID: List<String>,
    private val result: List<String>,
    private val result1: List<String>,
    private val Image: List<String>
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

    @SuppressLint("SetTextI18n")
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
        show.text = "${inforID[position]}"

        // set image to ImageView in dialog
        val image = dialog?.findViewById<ImageView>(R.id.imageView)
        val imagePath = Image[position]
        Glide.with(context)
            .load(imagePath)
            .into(image!!)


        val value = dialog?.findViewById<TextView>(R.id.TextShowResalt1)
        value?.text = "${result[position]}"

        val value1 = dialog?.findViewById<TextView>(R.id.TextShowResalt2)
        value1?.text = "${result1[position]}"

        dialog.findViewById<View>(R.id.button_close)?.setOnClickListener {
            dialog.dismiss()
        }
    }
}
