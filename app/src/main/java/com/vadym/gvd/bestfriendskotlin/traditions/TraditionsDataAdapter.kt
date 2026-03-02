package com.vadym.gvd.bestfriendskotlin.traditions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.vadym.gvd.bestfriendskotlin.R

class TraditionsAdapter(
    private val items: List<TraditionItem>,
    private val onItemClick: (TraditionItem) -> Unit
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
        val overlay: View          = view.findViewById(R.id.view_overlay)
        val tvTitle: TextView = view.findViewById(R.id.tv_tradition_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.grid_item, parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]

        holder.tvTitle.text = item.title

        // Фото — якщо є URL, завантажуй через Glide/Coil
        if (item.imageUrl != null) {
//            Glide.with(holder.ivBg)
//                .load(item.imageUrl)
//                .centerCrop()
//                .placeholder(R.drawable.bg_placeholder)
//                .into(holder.ivBg)
        } else {
            holder.ivBg.setImageResource(R.drawable.bg_placeholder)
        }

        holder.overlay.setBackgroundColor(overlayColors[position % overlayColors.size])

        holder.card.setOnClickListener { onItemClick(item) }
    }

    override fun getItemCount() = items.size
}

data class TraditionItem(
    val title: String,
    val imageUrl: String? = null
)




//class TraditionsDataAdapter(private val traditions: List<String>,
//                            private val itemClickListener: (Int) -> Unit) :
//    RecyclerView.Adapter<TraditionsDataAdapter.TraditionViewHolder>() {
//
//    class TraditionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val button: Button = view.findViewById(R.id.btn_tradition)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TraditionViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.grid_item, parent, false)
//        return TraditionViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: TraditionViewHolder, position: Int) {
//        holder.button.text = traditions[position]
//        holder.button.setOnClickListener { itemClickListener(position) }
//    }
//
//    override fun getItemCount(): Int = traditions.size
//}