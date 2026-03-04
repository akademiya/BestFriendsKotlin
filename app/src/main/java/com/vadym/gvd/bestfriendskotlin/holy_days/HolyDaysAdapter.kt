package com.vadym.gvd.bestfriendskotlin.holy_days

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.vadym.gvd.bestfriendskotlin.R

class HolyDaysAdapter (
    private val items: List<HolyDayItem>,
    private val onItemClick: (HolyDayItem) -> Unit
) : RecyclerView.Adapter<HolyDaysAdapter.VH>() {

    private val overlayColors = listOf(
        0xAA7B52F4.toInt(),
        0xAA00BCD4.toInt(),
        0xAA66BB6A.toInt(),
        0xAAFFB300.toInt(),
        0xAAF48FB1.toInt(),
        0xAA80CBC4.toInt(),
    )

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val card: CardView = view.findViewById(R.id.card_holy_day)
        val ivBg: ImageView = view.findViewById(R.id.iv_holy_day_bg)
        val overlay: View      = view.findViewById(R.id.view_overlay)
        val tvTitle: TextView = view.findViewById(R.id.tv_holy_day_title)
        val tvSection: TextView = view.findViewById(R.id.tv_holy_day_section)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.grid_item_holy_day, parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.tvTitle.text = holder.itemView.context.getString(item.titleRes)
        holder.ivBg.setImageResource(item.imageRes ?: R.drawable.bg_placeholder)
        holder.overlay.setBackgroundColor(overlayColors[position % overlayColors.size])
        holder.card.setOnClickListener { onItemClick(item) }
    }

    override fun getItemCount() = items.size
}