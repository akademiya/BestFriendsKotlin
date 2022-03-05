package com.vadym.gvd.bestfriendskotlin.father_kido

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vadym.gvd.bestfriendskotlin.R
import kotlinx.android.synthetic.main.item_father_kido.view.*

class FatherKidoAdapter(private val booksList: ArrayList<FatherKido>,
                        private val clickListener: (FatherKido) -> Unit) : androidx.recyclerview.widget.RecyclerView.Adapter<FatherKidoAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH (
            LayoutInflater.from(parent.context).inflate(R.layout.item_father_kido, parent, false)
    )

    override fun getItemCount(): Int { return booksList.size }

    override fun onBindViewHolder(holder: VH, position: Int) { holder.bind(booksList[position], clickListener) }

    class VH(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        fun bind(books: FatherKido, clickListener: (FatherKido) -> Unit) {
            itemView.let {
                it.name_book_kido.text = books.kidoBookName
                it.setOnClickListener { clickListener(books) }
            }
        }
    }
}