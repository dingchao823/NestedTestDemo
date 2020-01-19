package com.example.nestedtestdemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.nestedtestdemo.R

class SimpleRecyclerAdapter(var context : Context?) : RecyclerView.Adapter<SimpleRecyclerAdapter.ViewHolder>(){



    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.recycler_item_normal,
                p0,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.textView.text = position.toString()
        holder.textView.setOnClickListener {
            Toast.makeText(context, "点击了$position", Toast.LENGTH_SHORT).show()
        }

    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val textView : TextView = itemView as TextView
    }

}