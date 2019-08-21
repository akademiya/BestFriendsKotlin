package com.vadym.gvd.bestfriendskotlin.kido

import android.app.AlertDialog
import android.media.MediaPlayer
import android.os.Bundle
import android.os.SystemClock
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.text.TextUtils
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import com.google.android.gms.analytics.HitBuilders
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.kido.Chronometer.nextBeep
import com.vadym.gvd.bestfriendskotlin.kido.adapter.PersonAdapter
import com.vadym.gvd.bestfriendskotlin.kido.database.SqliteDatabase
import com.vadym.gvd.bestfriendskotlin.tracker
import kotlinx.android.synthetic.main.view_kido.*
import java.util.*


class PersonView : MainActivity() {
    private lateinit var allPerson: List<Person>
    private lateinit var listPersonEmpty: RelativeLayout
    private lateinit var database: SqliteDatabase
    private lateinit var adapter: PersonAdapter
    private lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_kido)
        toolbarButtonMenu()
        initializ()
        showOrHideFab()
        chronometer()
        tracker().setScreenName("Kido for Person")
        tracker().send(HitBuilders.ScreenViewBuilder().build())
    }

    private fun toolbarButtonMenu() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initializ() {
        fab.setOnClickListener { addTaskDialog() }
        listPersonEmpty = findViewById(R.id.list_kido_empty)

        itemTouchHelper = ItemTouchHelper(touchHelperCallback()).apply {
            attachToRecyclerView(rv_list_kido)
        }

        rv_list_kido.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv_list_kido.setHasFixedSize(true)

        database = SqliteDatabase.getInstance(this)
        allPerson = database.listPerson()

        if (allPerson.isNotEmpty()) {
            rv_list_kido.visibility = View.VISIBLE
            adapter = PersonAdapter(
                    personList = allPerson.sortedBy { it.personPosition },
                    context = this,
                    database = database,
                    onMoveItemTouch = { viewHolder -> onStartDrag(viewHolder) })
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
                val newPerson = Person(name, description, allPerson.lastIndex + 1)
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

        start.setOnClickListener {
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

        stop.setOnClickListener { Chronometer.stop() }
        reset.setOnClickListener {
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

    private fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }

    private fun touchHelperCallback() = object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
            val dragFlags: Int = ItemTouchHelper.UP.or(ItemTouchHelper.DOWN)
            val swipeFlags: Int = ItemTouchHelper.ACTION_STATE_DRAG
            return makeMovementFlags(dragFlags, swipeFlags)
        }

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            adapter.notifyItemMoved(viewHolder.adapterPosition, target.adapterPosition)
            drop(viewHolder.adapterPosition, target.adapterPosition)
            return true
        }

        override fun isLongPressDragEnabled(): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
    }

    fun drop(from: Int, to: Int) {
        if (from < to) {
            for (i in from until to) {
                Collections.swap(allPerson, i, i + 1)
            }
        } else {
            for (i in from downTo to + 1) {
                Collections.swap(allPerson, i, i - 1)
            }
        }

        allPerson.forEachIndexed { index, current ->
            current.personPosition = index
            database.updateSortPosition(current)
        }
    }
}