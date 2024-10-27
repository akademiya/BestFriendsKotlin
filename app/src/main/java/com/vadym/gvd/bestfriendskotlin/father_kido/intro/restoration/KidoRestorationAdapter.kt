package com.vadym.gvd.bestfriendskotlin.father_kido.intro.restoration

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.vadym.gvd.bestfriendskotlin.R

class KidoRestorationAdapter(private val booksKido: ArrayList<KidoRestoration>) : RecyclerView.Adapter<KidoRestorationAdapter.VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH (
        LayoutInflater.from(parent.context).inflate(R.layout.item_father_kido_intro, parent, false)
    )

    override fun getItemCount(): Int { return booksKido.size }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(booksKido[position])
    }

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        private val kidoTitle = view.findViewById<TextView>(R.id.kido_title)
        private val kidoDescription = view.findViewById<TextView>(R.id.kido_description)
        fun bind(books: KidoRestoration) {
            itemView.let {
                kidoTitle.text = books.textTitle
                kidoDescription.text = books.textDescription
            }
        }
    }
}