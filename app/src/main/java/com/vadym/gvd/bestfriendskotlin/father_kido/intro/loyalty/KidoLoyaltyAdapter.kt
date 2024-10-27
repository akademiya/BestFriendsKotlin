package com.vadym.gvd.bestfriendskotlin.father_kido.intro.loyalty

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vadym.gvd.bestfriendskotlin.R

class KidoLoyaltyAdapter(private val booksList: ArrayList<KidoLoyalty>) : RecyclerView.Adapter<KidoLoyaltyAdapter.VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_father_kido_intro, parent, false)
    )

    override fun getItemCount(): Int { return booksList.size }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(booksList[position])
    }

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        private val kidoTitle = view.findViewById<TextView>(R.id.kido_title)
        private val kidoDescription = view.findViewById<TextView>(R.id.kido_description)
        fun bind(books: KidoLoyalty) {
            itemView.let {
                kidoTitle.text = books.textTitle
                kidoDescription.text = books.textDescription
            }
        }
    }
}