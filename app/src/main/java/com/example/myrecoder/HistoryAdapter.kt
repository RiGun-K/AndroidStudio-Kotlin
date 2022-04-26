package com.example.myrecoder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecoder.model.MyRecord

class HistoryAdapter:RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
//    private lateinit var data:List<MyRecord>
    private var data:List<MyRecord> = listOf()

    fun updateData(data:List<MyRecord>){
        this.data = data
        notifyDataSetChanged()
    }

    fun getItem(index:Int):MyRecord{
        return data[index]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(v)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val record = data[position]
        holder.textViewTime.text = record.time
        holder.textViewFood.text = record.food
    }
    override fun getItemCount(): Int {
        return data.size
    }
    class HistoryViewHolder(v: View): RecyclerView.ViewHolder(v){
        val textViewTime: TextView = v.findViewById(R.id.textViewTime)
        val textViewFood:TextView = v.findViewById(R.id.textViewFood)
    }

}