package com.vadym.gvd.bestfriendskotlin.father_kido

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.vadym.gvd.bestfriendskotlin.R

class FatherKidoAdapter(private val booksList: ArrayList<FatherKido>,
                        private val clickListener: (FatherKido) -> Unit) : RecyclerView.Adapter<FatherKidoAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH (
            LayoutInflater.from(parent.context).inflate(R.layout.item_father_kido, parent, false)
    )

    override fun getItemCount(): Int { return booksList.size }

    override fun onBindViewHolder(holder: VH, position: Int) { holder.bind(booksList[position], clickListener) }

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        private val nameOfBook = view.findViewById<TextView>(R.id.name_book_kido)
        fun bind(books: FatherKido, clickListener: (FatherKido) -> Unit) {
            itemView.let {
                nameOfBook.text = books.kidoBookName
                it.setOnClickListener { clickListener(books) }
            }
        }
    }
}