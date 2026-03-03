package com.vadym.gvd.bestfriendskotlin.traditions

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.vadym.gvd.bestfriendskotlin.R

class TraditionsAdapter(
    private val items: List<TraditionItem>,
    private val onItemClick: (position: Int) -> Unit
) : RecyclerView.Adapter<TraditionsAdapter.VH>() {

    private val overlayColors = listOf(
        0x997B52F4.toInt(),  // фіолетовий
        0x9900BCD4.toInt(),  // блакитний
        0x9966BB6A.toInt(),  // зелений
        0x99FFB300.toInt(),  // жовтий
        0x99F48FB1.toInt(),  // рожевий
        0x9980CBC4.toInt(),  // м'ятний
    )

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val card: MaterialCardView = view.findViewById(R.id.card_tradition)
        val ivBg: ImageView = view.findViewById(R.id.iv_tradition_bg)
        val tvTitle: TextView = view.findViewById(R.id.tv_tradition_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.grid_item, parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]

        holder.tvTitle.text = item.title

        if (item.imageRes != null) {
            holder.ivBg.setImageResource(item.imageRes)
        } else {
            holder.ivBg.setImageResource(R.drawable.bg_placeholder)
        }

        val baseColor = overlayColors[position % overlayColors.size]

        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(
                Color.TRANSPARENT,
                baseColor
            )
        )

        holder.tvTitle.background = gradientDrawable

        holder.card.setOnClickListener { onItemClick(position) }
    }

    override fun getItemCount() = items.size
}


data class TraditionItem(
    val title: String,
    @DrawableRes val imageRes: Int? = null
)