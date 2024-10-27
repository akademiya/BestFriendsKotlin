package com.vadym.gvd.bestfriendskotlin.father_kido.intro.yongye

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.vadym.gvd.bestfriendskotlin.R

class KidoYongyeAdapter(private val booksList: ArrayList<KidoYongye>) : RecyclerView.Adapter<KidoYongyeAdapter.VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_father_kido_intro, parent, false)
    )

    override fun getItemCount(): Int { return booksList.size }

    override fun onBindViewHolder(holder: VH, position: Int) { holder.bind(booksList[position]) }

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        private val kidoTitle = view.findViewById<TextView>(R.id.kido_title)
        private val kidoDescription = view.findViewById<TextView>(R.id.kido_description)
        fun bind(books: KidoYongye) {
            itemView.let {
                kidoTitle.text = books.textTitle
                kidoDescription.text = books.textDescription
            }
        }
    }
}