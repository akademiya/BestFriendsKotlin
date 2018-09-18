package com.vadym.gvd.bestfriendskotlin.kido.adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.kido.Person
import com.vadym.gvd.bestfriendskotlin.kido.database.SqliteDatabase

class PersonAdapter(private val personList: List<Person>, val context: Context, private val database: SqliteDatabase) : RecyclerView.Adapter<PersonAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_kido, parent, false)
    )

    override fun getItemCount(): Int { return personList.size }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: VH, position: Int) {
        val singlePerson = personList[position]

        holder.let {
            it.personName?.text = singlePerson.personName
            it.personDescription?.text = singlePerson.personDescription
            it.listView?.setOnLongClickListener { _ ->
                holder.listReview?.visibility = View.VISIBLE
                holder.personDescription?.setTextColor(Color.LTGRAY)
                false
            }
            it.goBack?.setOnClickListener { _ ->
                holder.listReview?.visibility = View.GONE
                holder.personDescription?.setTextColor(Color.DKGRAY)
            }
            it.deleteItem?.setOnClickListener { _ ->
                database.deletePerson(singlePerson.personId)
                (context as Activity).finish()
                context.startActivity(context.intent)
            }
            it.editItem?.setOnClickListener { editTaskDialog(singlePerson) }

            if (singlePerson.personDescription!!.isBlank()) {
                it.personDescription?.visibility = View.GONE
            }
        }
    }

    private fun editTaskDialog(person: Person) {
        val inflater = LayoutInflater.from(context)
        val subView = inflater.inflate(R.layout.item_edit_list_person, null)

        val nameField = subView.findViewById<EditText>(R.id.create_person_name)
        val descriptionField = subView.findViewById<EditText>(R.id.create_person_description)
        nameField.setText(person.personName)
        descriptionField.setText(person.personDescription)

        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.edit_person)
        builder.setView(subView)
        builder.create()
        builder.setPositiveButton(R.string.edit_person) { _, _ ->
            val name = nameField.text.toString()
            val description = descriptionField.text.toString()
            database.updatePerson(Person(person.personId, name, description))

            (context as Activity).finish()
            context.startActivity(context.intent)
        }

        builder.setNegativeButton(R.string.cancel) { _, _ -> Toast.makeText(context, R.string.task_cancelled, Toast.LENGTH_SHORT).show() }
        builder.show()
    }


    class VH(view: View?) : RecyclerView.ViewHolder(view) {
        val personName = view?.findViewById<TextView>(R.id.person_name)
        val personDescription = view?.findViewById<TextView>(R.id.person_description)
        val listReview = view?.findViewById<FrameLayout>(R.id.listReview)
        val listView = view?.findViewById<RelativeLayout>(R.id.listView)
        val goBack = view?.findViewById<ImageView>(R.id.go_back)
        val deleteItem = view?.findViewById<ImageView>(R.id.delete_item)
        val editItem = view?.findViewById<ImageView>(R.id.edit_item)
    }
}