package com.vadym.gvd.bestfriendskotlin.calendar

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vadym.gvd.bestfriendskotlin.R

class CalendarAdapter (private val days: List<HeavenlyCalendarView.CalendarDay>) :
    RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {

    private var selectedPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_calendar, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day = days[position]
        holder.gregorianText.text = day.gregorian
        holder.lunarText.text = day.lunar

        if (day.isToday) {
            holder.itemView.setBackgroundResource(R.drawable.current_day_bg)
            holder.lunarText.visibility = View.GONE
        } else if (day.isAnshiil) {
            holder.itemView.setBackgroundResource(R.drawable.anshiil_day_bg)
        } else if (selectedPosition == position && day.gregorian.isNotEmpty()) {
            val border = GradientDrawable()
            border.setStroke(4, holder.itemView.resources.getColor(R.color.accent_darker))
            border.cornerRadius = 6F
            border.setColor(holder.itemView.resources.getColor(R.color.calendar_day_bg))
            holder.itemView.background = border
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT)
            holder.lunarText.visibility = View.VISIBLE
        }

        holder.itemView.setOnClickListener {
            selectedPosition = position
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = days.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val gregorianText: TextView = view.findViewById(R.id.txt1)
        val lunarText: TextView = view.findViewById(R.id.txt2)
    }
}