package com.vadym.gvd.bestfriendskotlin.traditions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.vadym.gvd.bestfriendskotlin.R


class TraditionsDataAdapter(
    private val context: Context,
    private val traditionsList: List<String>
) : BaseAdapter() {

    override fun getCount(): Int {
        return traditionsList.size
    }

    override fun getItem(position: Int): Any {
        return traditionsList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(
            R.layout.grid_item, parent, false
        )

        val textView: TextView = view.findViewById(R.id.item_text)
        textView.text = traditionsList[position]

        return view
    }

}