package com.vadym.gvd.bestfriendskotlin.condition

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.analytics.HitBuilders
import com.vadym.gvd.bestfriendskotlin.*
import com.vadym.gvd.bestfriendskotlin.condition.adapter.ConditionAdapter
import com.vadym.gvd.bestfriendskotlin.condition.database.ConditionSqlDB
import kotlinx.android.synthetic.main.view_condition.*
import java.util.*

class ConditionView : MainActivity() {

    private lateinit var emptyPage: RelativeLayout
    private lateinit var listCondition: List<Condition>
    private lateinit var database: ConditionSqlDB
    private lateinit var adapter: ConditionAdapter
    private lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_condition)
        toolbarButtonMenu()
        showOrHideFab()
        tracker().setScreenName("Condition Screen")
        tracker().send(HitBuilders.ScreenViewBuilder().build())

        fab.setOnClickListener { addTaskDialog() }
        emptyPage = findViewById(R.id.list_condition_empty)

        itemTouchHelper = ItemTouchHelper(touchHelperCallback()).apply {
            attachToRecyclerView(view_list_condition)
        }

        view_list_condition.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        view_list_condition.setHasFixedSize(true)
        database = ConditionSqlDB.getInstance(this)
        listCondition = database.listConditions()

        if (listCondition.isNotEmpty()) {
            view_list_condition.visibility = View.VISIBLE
            adapter = ConditionAdapter(this, database, listCondition) {
                onStartDrag(it)
            }
            view_list_condition.adapter = adapter
        } else {
            view_list_condition.visibility = View.GONE
            emptyPage.visibility = View.VISIBLE
        }
    }

    private fun toolbarButtonMenu() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun showOrHideFab() {
        view_list_condition.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && fab.visibility == View.VISIBLE) {
                    fab.hide()
                } else if (dy < 0 && fab.visibility != View.VISIBLE) {
                    fab.show()
                }
            }
        })
    }

    private fun addTaskDialog() {
        val subView = LayoutInflater.from(this).inflate(R.layout.item_edit_list_condition, null)

        val lider = subView.findViewById<EditText>(R.id.create_lider)
        val duration = subView.findViewById<EditText>(R.id.create_duration)
        val dateFrom = subView.findViewById<Button>(R.id.create_date_from)
        val condition = subView.findViewById<EditText>(R.id.create_condition)
        val pubGoal = subView.findViewById<EditText>(R.id.create_public_goal)
        dateFrom.visibility = View.VISIBLE
        datePickerDialog(dateFrom)

        AlertDialog.Builder(this).apply {
            setTitle(R.string.add_new_condition)
            setView(subView)
            create()
            setPositiveButton(R.string.add_condition) { _, _ ->
                val liderFild = lider.text.toString()
                val durationFild = duration.text.toString()
                val conditionFild = condition.text.toString()
                val pubGoalFild = pubGoal.text.toString()

                if (TextUtils.isEmpty(liderFild)
                        || TextUtils.isEmpty(durationFild)
                        || TextUtils.isEmpty(conditionFild)
                        || TextUtils.isEmpty(pubGoalFild)) {
                    Toast.makeText(this@ConditionView, R.string.something_wrong, Toast.LENGTH_SHORT).show()
                } else {
                    val from = if (dateFrom.text == resources.getString(R.string.date_from)) {
                        getToday()
                    } else dateFrom.text.toString()

                    val newCondition = Condition(
                            lider = liderFild,
                            duration = durationFild,
                            today = from,
                            condition = conditionFild,
                            pubGoal = pubGoalFild,
                            perGoal = "test2",
                            conditionPosition = listCondition.lastIndex + 1)
                    database.addCondition(newCondition)
                    restartActivity(this@ConditionView)
                }
            }
            setNegativeButton(R.string.cancel) { _, _ -> Toast.makeText(this@ConditionView, R.string.task_cancelled, Toast.LENGTH_SHORT).show() }
            show()
        }
    }

    private fun datePickerDialog(dateFrom: Button) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        dateFrom.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val monthPlus = monthOfYear + 1
                val currentMonth = if (monthPlus.toString().length == 1) { "0$monthPlus" } else "$monthPlus"
                val date = "$year$currentMonth$dayOfMonth"
                dateFrom.text = date.formatterDate()
            }, year, month, day)
            datePickerDialog.show()
        }
    }

    private fun getToday() : String {
        val calendar = Calendar.getInstance()
        return calendar.time.formatterDate()
    }


    private fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }

    private fun touchHelperCallback() = object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
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
                Collections.swap(listCondition, i, i + 1)
            }
        } else {
            for (i in from downTo to + 1) {
                Collections.swap(listCondition, i, i - 1)
            }
        }

        listCondition.forEachIndexed { index, current ->
            current.conditionPosition = index
            database.updateSortPosition(current)
        }
    }
}