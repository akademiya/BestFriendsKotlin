package com.example.user.bestfriendskotlin.kido

import android.app.AlertDialog
import android.media.MediaPlayer
import android.os.Bundle
import android.os.SystemClock
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.example.user.bestfriendskotlin.MainActivity
import com.example.user.bestfriendskotlin.R
import com.example.user.bestfriendskotlin.kido.Chronometer.nextBeep
import com.example.user.bestfriendskotlin.kido.adapter.PersonAdapter
import com.example.user.bestfriendskotlin.kido.database.SqliteDatabase
import com.example.user.bestfriendskotlin.tracker
import kotlinx.android.synthetic.main.view_kido.*

class PersonView : MainActivity() {
    private lateinit var allPerson: List<Person>
    private lateinit var listPersonEmpty: RelativeLayout
    private lateinit var database: SqliteDatabase
    private lateinit var adapter: PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_kido)
        toolbar_button_menu()
        initializ()
        showOrHideFab()
        chronometer()
        tracker().setScreenName("Kido for Person")
    }

    private fun toolbar_button_menu() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { _ -> onBackPressed() }
    }

    fun initializ() {
        fab.setOnClickListener { _ -> addTaskDialog() }
        listPersonEmpty = findViewById(R.id.list_kido_empty)

        rv_list_kido.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv_list_kido.setHasFixedSize(true)

        database = SqliteDatabase.getInstance(this)
        allPerson = database.listPerson()

        if (allPerson.isNotEmpty()) {
            rv_list_kido.visibility = View.VISIBLE
            adapter = PersonAdapter(allPerson, this, database)
            rv_list_kido.adapter = adapter
        } else {
            rv_list_kido.visibility = View.GONE
            listPersonEmpty.visibility = View.VISIBLE
        }
    }

    private fun addTaskDialog() {
        val inflater = LayoutInflater.from(this)
        val subView = inflater.inflate(R.layout.item_edit_list_person, null)

        val nameField = subView.findViewById<EditText>(R.id.create_person_name)
        val descriptionFiled = subView.findViewById<EditText>(R.id.create_person_description)

        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.add_new_person)
        builder.setView(subView)
        builder.create()

        builder.setPositiveButton(R.string.add_person) { _, _ ->
            val name = nameField.text.toString()
            val description = descriptionFiled.text.toString()

            if (TextUtils.isEmpty(name)) {
                Toast.makeText(this, R.string.something_wrong, Toast.LENGTH_SHORT).show()
            } else {
                val newPerson = Person(name, description)
                database.addPerson(newPerson)

                finish()
                startActivity(intent)
            }
        }

        builder.setNegativeButton(R.string.cancel) { _, _ -> Toast.makeText(this, R.string.task_cancelled, Toast.LENGTH_SHORT).show() }
        builder.show()
    }

    private fun chronometer() {
        val mp = MediaPlayer.create(this, R.raw.ton)
        chronometer.text = DateUtils.formatElapsedTime(0)

        start.setOnClickListener { _ ->
            Chronometer.base = SystemClock.elapsedRealtime()
            Chronometer.start()
        }


        Chronometer.setOnTick {
            val now = SystemClock.elapsedRealtime()
            val elapsedMillis = now - Chronometer.base

            chronometer.text = DateUtils.formatElapsedTime(elapsedMillis / 1000)
            if (elapsedMillis < Chronometer.nextBeep)
                return@setOnTick

            when {
                elapsedMillis >= 2400000 -> {
                    Toast.makeText(this, resources.getString(R.string.min_40), Toast.LENGTH_SHORT).show()
                    mp.start()
                }
                elapsedMillis >= 1260000 -> {
                    Toast.makeText(this, resources.getString(R.string.min_21), Toast.LENGTH_SHORT).show()
                    mp.start()
                    nextBeep = 2400000
                }
                elapsedMillis >= 720000 -> {
                    Toast.makeText(this, resources.getString(R.string.min_12), Toast.LENGTH_SHORT).show()
                    mp.start()
                    nextBeep = 1260000
                }
                elapsedMillis >= 420000 -> {
                    Toast.makeText(this, resources.getString(R.string.min_7), Toast.LENGTH_SHORT).show()
                    mp.start()
                    nextBeep = 720000
                }
                elapsedMillis >= 180000 -> {
                    Toast.makeText(this, resources.getString(R.string.min_3), Toast.LENGTH_SHORT).show()
                    mp.start()
                    nextBeep = 420000
                }
            }
        }

        stop.setOnClickListener { _ ->
            Chronometer.stop()
        }

        reset.setOnClickListener { _ ->
            Chronometer.stop()
            chronometer.text = DateUtils.formatElapsedTime(0)
        }
    }

    private fun showOrHideFab() {
        rv_list_kido.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && fab.visibility == View.VISIBLE) {
                    fab.hide()
                } else if (dy < 0 && fab.visibility != View.VISIBLE) {
                    fab.show()
                }
            }
        })
    }

}