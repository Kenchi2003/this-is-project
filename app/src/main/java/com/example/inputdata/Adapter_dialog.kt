package com.example.inputdata.com.example.inputdata
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.inputdata.AppDatabase
import com.example.inputdata.R
import com.example.inputdata.sentdataDao

class Adapter_dialog(private val item: List<String>, private val inforID: String) : RecyclerView.Adapter<Adapter_dialog.VHolder>() {
    override fun getItemCount(): Int {
        return item.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_showrecord, parent, false)
        return VHolder(v)
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        val ID =
    }
    class VHolder(v: View) : RecyclerView.ViewHolder(v){
        val textShow : TextView = v.findViewById(R.id.textView_show)
    }
    //dgrhr
}