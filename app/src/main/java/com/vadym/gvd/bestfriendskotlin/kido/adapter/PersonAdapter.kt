package com.vadym.gvd.bestfriendskotlin.kido.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.kido.Person
import com.vadym.gvd.bestfriendskotlin.kido.database.SqliteDatabase
import com.vadym.gvd.bestfriendskotlin.restartActivity


class PersonAdapter(private val personList: List<Person>,
                    private val context: Context,
                    private val database: SqliteDatabase,
                    private val onMoveItemTouch: (viewHolder: VH) -> Unit) : RecyclerView.Adapter<PersonAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_kido, parent, false)
    )

    override fun getItemCount(): Int { return personList.size }

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: VH, position: Int) {
        val singlePerson = personList[position]

        holder.apply {
            personName?.text = singlePerson.personName
            personDescription?.text = singlePerson.personDescription
            personPhoto.setImageBitmap(singlePerson.personPhoto)
            listView?.setOnLongClickListener {
                holder.listReview?.visibility = View.VISIBLE
                holder.counter?.visibility = View.GONE
                holder.cardImg.visibility = View.GONE
                holder.personDescription?.setTextColor(Color.LTGRAY)
                true
            }

            counter?.setBackgroundResource(R.drawable.ic_circle)
            counter?.text = singlePerson.counter.toString()
            counter?.setOnClickListener {
                singlePerson.counter++
                notifyDataSetChanged()
            }

            ivMoveItem?.setOnTouchListener { _, event ->
                if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                    holder.listReview?.background = context.getDrawable(R.drawable.button_green_bordered)
                    onMoveItemTouch(holder)
                }
                return@setOnTouchListener false
            }

            goBack?.setOnClickListener {
                holder.listReview?.visibility = View.GONE
                holder.counter?.visibility = View.VISIBLE
                holder.cardImg.visibility = View.VISIBLE
                holder.personDescription?.setTextColor(Color.DKGRAY)
                holder.listReview?.setBackgroundColor(context.resources.getColor(R.color.icon_pressed))

                restartActivity(context)
            }
            deleteItem?.setOnClickListener {
                database.deletePerson(singlePerson.personId)
                restartActivity(context)
            }
            editItem?.setOnClickListener { editTaskDialog(singlePerson) }

            if (singlePerson.personDescription!!.isNotBlank()) {
                personDescription?.visibility = View.VISIBLE
            } else
                personDescription?.visibility = View.GONE
        }
    }

    private fun editTaskDialog(person: Person) {
        val inflater = LayoutInflater.from(context)
        val subView = inflater.inflate(R.layout.item_edit_list_person, null)

        val nameField = subView.findViewById<EditText>(R.id.create_person_name)
        val descriptionField = subView.findViewById<EditText>(R.id.create_person_description)
        val imgField = subView.findViewById<ImageView>(R.id.upload_img_person)
        val btn = subView.findViewById<Button>(R.id.btn_select_photo)
        nameField.setText(person.personName)
        descriptionField.setText(person.personDescription)
        imgField.setImageBitmap(person.personPhoto)
        btn.visibility = View.GONE


        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.edit_person)
        builder.setView(subView)
        builder.create()
        builder.setPositiveButton(R.string.edit_person) { _, _ ->
            val name = nameField.text.toString()
            val description = descriptionField.text.toString()
            database.updatePerson(Person(person.personId, name, description, person.personPhoto, person.personPosition))

            restartActivity(context)
        }

        builder.setNegativeButton(R.string.cancel) { _, _ -> Toast.makeText(context, R.string.task_cancelled, Toast.LENGTH_SHORT).show() }
        builder.show()
    }


    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val personName = view.findViewById<TextView>(R.id.person_name)
        val personDescription = view.findViewById<TextView>(R.id.person_description)
        val personPhoto = view.findViewById<ImageView>(R.id.person_img)
        val counter = view.findViewById<TextView>(R.id.tv_counter)
        val listReview = view.findViewById<FrameLayout>(R.id.listReview)
        val listView = view.findViewById<RelativeLayout>(R.id.listView)
        val goBack = view.findViewById<ImageView>(R.id.go_back)
        val deleteItem = view.findViewById<ImageView>(R.id.delete_item)
        val editItem = view.findViewById<ImageView>(R.id.edit_item)
        val ivMoveItem = view.findViewById<ImageView>(R.id.iv_move_item)
        val cardImg = view.findViewById<CardView>(R.id.cv_person_img)
    }
}