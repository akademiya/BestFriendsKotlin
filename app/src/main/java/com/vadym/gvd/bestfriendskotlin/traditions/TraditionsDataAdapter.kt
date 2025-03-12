package com.vadym.gvd.bestfriendskotlin.traditions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.vadym.gvd.bestfriendskotlin.R


class TraditionsDataAdapter(private val traditions: List<String>,
                            private val itemClickListener: (Int) -> Unit) :
    RecyclerView.Adapter<TraditionsDataAdapter.TraditionViewHolder>() {

    class TraditionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val button: Button = view.findViewById(R.id.btn_tradition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TraditionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.grid_item, parent, false)
        return TraditionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TraditionViewHolder, position: Int) {
        holder.button.text = traditions[position]
        holder.button.setOnClickListener { itemClickListener(position) }
    }

    override fun getItemCount(): Int = traditions.size
}