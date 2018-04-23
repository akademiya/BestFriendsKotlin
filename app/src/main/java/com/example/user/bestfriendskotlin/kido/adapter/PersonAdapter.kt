package com.example.user.bestfriendskotlin.kido.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.user.bestfriendskotlin.R
import com.example.user.bestfriendskotlin.kido.Person

class PersonAdapter(val userList: ArrayList<Person>) : RecyclerView.Adapter<PersonAdapter.VH>() {

//    private var listPerson = emptyList<Person>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_kido, parent, false)
    )

    override fun getItemCount(): Int { return userList.size }

    override fun onBindViewHolder(holder: VH, position: Int) {
//        val singlePerson = listPerson[position]
        holder.let { it.personName?.text = userList[position].personName }

    }

//    fun setPersonList(listPerson: List<Person>) = updateListWithDiffs(
//        this.listPerson, listPerson,
//        {this.listPerson = it},
//        {o, n -> o.id == n.id})


    class VH(view: View?) : RecyclerView.ViewHolder(view) {
        val personName = view?.findViewById<TextView>(R.id.person_name)
    }
}