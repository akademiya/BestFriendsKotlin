package com.vadym.gvd.bestfriendskotlin.father_kido.intro.faith

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vadym.gvd.bestfriendskotlin.R
import kotlinx.android.synthetic.main.item_father_kido_intro.view.*

class KidoFaithAdapter(private val booksList: ArrayList<KidoFaith>) : androidx.recyclerview.widget.RecyclerView.Adapter<KidoFaithAdapter.VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH (
        LayoutInflater.from(parent.context).inflate(R.layout.item_father_kido_intro, parent, false)
    )

    override fun getItemCount(): Int { return booksList.size }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(booksList[position])
    }

    class VH(val view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        fun bind(books: KidoFaith) {
            itemView.let {
                it.kido_title.text = books.textTitle
                it.kido_description.text = books.textDescription
            }
        }
    }
}