package com.example.inputdata.com.example.inputdata
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.inputdata.R

class Adapter_dialog(val context: Context, dataForID: String, private val inforID: List<String>) : RecyclerView.Adapter<Adapter_dialog.VHolder>() {
    override fun getItemCount(): Int {
        return inforID.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_showrecord, parent, false)
        return VHolder(v)
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.textShow.text = inforID[position]
    }
    class VHolder(v: View) : RecyclerView.ViewHolder(v){
        val textShow : TextView = v.findViewById(R.id.textView_show)
    }
    //dgrhr
}