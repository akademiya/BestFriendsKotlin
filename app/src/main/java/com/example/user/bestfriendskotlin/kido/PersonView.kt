package com.example.user.bestfriendskotlin.kido

import android.app.AlertDialog
import android.media.MediaPlayer
import android.os.Bundle
import android.os.SystemClock
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.example.user.bestfriendskotlin.MainActivity
import com.example.user.bestfriendskotlin.R
import com.example.user.bestfriendskotlin.kido.adapter.PersonAdapter
import com.example.user.bestfriendskotlin.kido.database.SqliteDatabase
import com.example.user.bestfriendskotlin.tracker
import kotlinx.android.synthetic.main.view_kido.*

class PersonView : MainActivity(), IPersonView {
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
        var flag3 = false
        var flag7 = false
        var flag12 = false
        var flag21 = false
        var flag40 = false

        start.setOnClickListener { _ ->
            chronometer.base = SystemClock.elapsedRealtime()
            chronometer.start()
        }

        chronometer.onChronometerTickListener = Chronometer.OnChronometerTickListener {
            val elapsedMillis = SystemClock.elapsedRealtime() - chronometer.base

            if (elapsedMillis >= 180000 && flag3 == false) {
                Toast.makeText(this, "Прошло 3 минуты", Toast.LENGTH_SHORT).show()
                mp.start()
                flag3 = true
            } else if(elapsedMillis >= 420000 && flag7 == false) {
                Toast.makeText(this, "Прошло 7 минут", Toast.LENGTH_SHORT).show()
                mp.start()
                flag7 = true
            } else if(elapsedMillis >= 720000 && flag12 == false) {
                Toast.makeText(this, "Прошло 12 минут", Toast.LENGTH_SHORT).show()
                mp.start()
                flag12 = true
            } else if(elapsedMillis >= 1260000 && flag21 == false) {
                Toast.makeText(this, "Прошло 21 минута", Toast.LENGTH_SHORT).show()
                mp.start()
                flag21 = true
            } else if(elapsedMillis >= 2400000 && flag40 == false) {
                Toast.makeText(this, "Прошло 40 минут", Toast.LENGTH_SHORT).show()
                mp.start()
                flag40 = true
            }
        }

        stop.setOnClickListener { _ ->
            chronometer.stop()
            flag3 = false
            flag7 = false
            flag12 = false
            flag21 = false
            flag40 = false
        }

        reset.setOnClickListener { _ ->
            chronometer.base = SystemClock.elapsedRealtime()
            flag3 = false
            flag7 = false
            flag12 = false
            flag21 = false
            flag40 = false
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