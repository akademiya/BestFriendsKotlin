package com.vadym.gvd.bestfriendskotlin.father_kido.intro.chamsaran

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vadym.gvd.bestfriendskotlin.R

class KidoChamsaranAdapter(private val booksList: ArrayList<KidoChamsaran>) : RecyclerView.Adapter<KidoChamsaranAdapter.VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_father_kido_intro, parent, false)
    )

    override fun getItemCount() = booksList.size

    override fun onBindViewHolder(holder: VH, position: Int) { holder.bind(booksList[position], position) }

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        private val kidoTitle: TextView = view.findViewById(R.id.kido_title)
        private val kidoDescription: TextView = view.findViewById(R.id.kido_description)
        private val dividerRow: LinearLayout = view.findViewById(R.id.divider_row)
        private val badge: TextView = view.findViewById(R.id.kido_badge)

        fun bind(item: KidoChamsaran, position: Int) {
            kidoTitle.text = item.textTitle
            kidoDescription.text = item.textDescription
            badge.text = itemView.context.getString(R.string.kido_counter, "${position + 1}")
            dividerRow.visibility = if (position == 0) View.GONE else View.VISIBLE
        }
    }
}