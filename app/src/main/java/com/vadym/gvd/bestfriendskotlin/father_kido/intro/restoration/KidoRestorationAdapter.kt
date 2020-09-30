package com.vadym.gvd.bestfriendskotlin.father_kido.intro.restoration

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vadym.gvd.bestfriendskotlin.R
import kotlinx.android.synthetic.main.item_father_kido_intro.view.*

class KidoRestorationAdapter(private val booksKido: ArrayList<KidoRestoration>) : androidx.recyclerview.widget.RecyclerView.Adapter<KidoRestorationAdapter.VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH (
        LayoutInflater.from(parent.context).inflate(R.layout.item_father_kido_intro, parent, false)
    )

    override fun getItemCount(): Int { return booksKido.size }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(booksKido[position])
    }

    class VH(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        fun bind(books: KidoRestoration) {
            itemView.let {
                it.kido_title.text = books.textTitle
                it.kido_description.text = books.textDescription
            }
        }
    }
}