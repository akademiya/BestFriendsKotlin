package com.example.user.bestfriendskotlin.father_kido.intro.reshimosty

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.bestfriendskotlin.R
import kotlinx.android.synthetic.main.item_father_kido_intro.view.*

class KidoReshimostyAdapter(private val booksList: ArrayList<KidoReshimosty>) : RecyclerView.Adapter<KidoReshimostyAdapter.VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_father_kido_intro, parent, false)
    )

    override fun getItemCount(): Int { return booksList.size }

    override fun onBindViewHolder(holder: VH, position: Int) { holder.bind(booksList[position]) }

    class VH(view: View?) : RecyclerView.ViewHolder(view) {
        fun bind(books: KidoReshimosty) {
            itemView.let {
                it.kido_title.text = books.textTitle
                it.kido_description.text = books.textDescription
            }
        }
    }
}