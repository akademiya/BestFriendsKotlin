package com.example.user.bestfriendskotlin.kido

import android.app.AlertDialog
import android.media.MediaPlayer
import android.os.Bundle
import android.os.SystemClock
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import android.widget.Chronometer
import com.example.user.bestfriendskotlin.MainActivity
import com.example.user.bestfriendskotlin.R
import com.example.user.bestfriendskotlin.kido.adapter.PersonAdapter
import com.example.user.bestfriendskotlin.kido.database.SqliteDatabase

class PersonView : MainActivity() {
    private lateinit var rv: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var allPerson: List<Person>
    private lateinit var listPersonEmpty: RelativeLayout
    private lateinit var database: SqliteDatabase
    private lateinit var adapter: PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_kido)
        toolbar_button_menu()
        init()
        showOrHideFab()
        chronometer()
    }

    private fun toolbar_button_menu() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { _ -> onBackPressed() }

//        val button_menu: ImageView = findViewById(R.id.button_menu)
//        button_menu.setOnClickListener { _ -> drawer_layout.openDrawer(Gravity.START) }
    }

    fun init() {
        rv = findViewById(R.id.rv_list_kido)
        fab = findViewById(R.id.fab)
        fab.setOnClickListener { _ -> addTaskDialog() }
        listPersonEmpty = findViewById(R.id.list_kido_empty)

        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv.setHasFixedSize(true)

        database = SqliteDatabase(this)
        allPerson = database.listPerson()

        if (allPerson.isNotEmpty()) {
            rv.visibility = View.VISIBLE
            adapter = PersonAdapter(allPerson, this, database)
            rv.adapter = adapter
        } else {
            rv.visibility = View.GONE
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
        val mChronometer: Chronometer = findViewById(R.id.chronometer)
        val startChronometer: Button = findViewById(R.id.start)
        val stopChronometer: Button = findViewById(R.id.stop)
        val restartChronometer: Button = findViewById(R.id.reset)
        val mp = MediaPlayer.create(this, R.raw.ton)
        var flag3 = false
        var flag7 = false
        var flag12 = false
        var flag21 = false
        var flag40 = false

        startChronometer.setOnClickListener { _ ->
            mChronometer.apply {
                base = SystemClock.elapsedRealtime()
                start()
            }
        }

        mChronometer.onChronometerTickListener = Chronometer.OnChronometerTickListener {
            val elapsedMillis = SystemClock.elapsedRealtime() - mChronometer.base

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

        stopChronometer.setOnClickListener { _ -> mChronometer.stop() }
        restartChronometer.setOnClickListener { _ -> mChronometer.base = SystemClock.elapsedRealtime() }
    }

    private fun showOrHideFab() {
        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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