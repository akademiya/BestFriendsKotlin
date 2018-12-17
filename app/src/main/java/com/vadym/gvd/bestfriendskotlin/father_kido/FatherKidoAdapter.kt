package com.vadym.gvd.bestfriendskotlin.father_kido

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vadym.gvd.bestfriendskotlin.R
import kotlinx.android.synthetic.main.item_father_kido.view.*

class FatherKidoAdapter(private val booksList: ArrayList<FatherKido>,
                        private val clickListener: (FatherKido) -> Unit) : RecyclerView.Adapter<FatherKidoAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH (
            LayoutInflater.from(parent.context).inflate(R.layout.item_father_kido, parent, false)
    )

    override fun getItemCount(): Int { return booksList.size }

    override fun onBindViewHolder(holder: VH, position: Int) { holder.bind(booksList[position], clickListener) }

    class VH(view: View?) : RecyclerView.ViewHolder(view) {
        fun bind(books: FatherKido, clickListener: (FatherKido) -> Unit) {
            itemView.let {
                it.name_book_kido.text = books.kidoBookName
                it.setOnClickListener { clickListener(books) }
                it.iv_new_books.visibility = when(books.booksID) {
                    13 -> View.VISIBLE
                    14 -> View.VISIBLE
                    15 -> View.VISIBLE
                    16 -> View.VISIBLE
                    17 -> View.VISIBLE
                    18 -> View.VISIBLE
                    19 -> View.VISIBLE
                    20 -> View.VISIBLE
                    21 -> View.VISIBLE
                    22 -> View.VISIBLE
                    23 -> View.VISIBLE
                    24 -> View.VISIBLE
                    25 -> View.VISIBLE
                    else -> View.GONE
                }
            }
        }
    }
}