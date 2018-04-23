package com.example.user.bestfriendskotlin.kido

import android.os.Bundle
import android.os.SystemClock
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import android.widget.*
import com.example.user.bestfriendskotlin.MainActivity
import com.example.user.bestfriendskotlin.R
import com.example.user.bestfriendskotlin.R.id.fab
import com.example.user.bestfriendskotlin.kido.adapter.PersonAdapter
import kotlinx.android.synthetic.main.activity_main.*

class PersonView : MainActivity() {

    private lateinit var rv: RecyclerView
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_kido)
        init()

        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        val persons = ArrayList<Person>()
        persons.add(Person(0, "Bob"))
        persons.add(Person(1, "Laya"))
        persons.add(Person(2, "Stiven"))
        persons.add(Person(3, "Nika"))
        persons.add(Person(4, "Garry"))

        val adapter = PersonAdapter(persons)
        rv.adapter = adapter

    }

    private fun addTaskDialog() {
        Toast.makeText(this, "Будет всплывать диалог", Toast.LENGTH_SHORT).show()
    }

    private fun showOrHideFab() {
        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && fab.getVisibility() == View.VISIBLE) {
                    fab.hide()
                } else if (dy < 0 && fab.getVisibility() != View.VISIBLE) {
                    fab.show()
                }
            }
        })
    }

    private fun chronometer() {
        val mChronometer: Chronometer = findViewById(R.id.chronometer)
        val startChronometer: Button = findViewById(R.id.start)
        val stopChronometer: Button = findViewById(R.id.stop)
        val restartChronometer: Button = findViewById(R.id.reset)

        startChronometer.setOnClickListener { _ ->
            mChronometer.apply {
                setBase(SystemClock.elapsedRealtime())
                start()
            }
        }

        stopChronometer.setOnClickListener { _ -> mChronometer.stop() }
        restartChronometer.setOnClickListener { _ -> mChronometer.setBase(SystemClock.elapsedRealtime()) }
    }

    fun init() {
        rv = findViewById(R.id.rv_list_kido)
        fab = findViewById(R.id.fab)
        fab.setOnClickListener { _ -> addTaskDialog() }

//        toolbar_button_menu()
        showOrHideFab()
        chronometer()
    }

    private fun toolbar_button_menu() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        val mDrawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val button_menu: ImageView = findViewById(R.id.button_menu)
        button_menu?.setOnClickListener { _ -> mDrawerLayout.openDrawer(Gravity.START) }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}