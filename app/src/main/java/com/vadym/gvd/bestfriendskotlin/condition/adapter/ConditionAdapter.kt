package com.vadym.gvd.bestfriendskotlin.condition.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.condition.Condition
import com.vadym.gvd.bestfriendskotlin.condition.database.ConditionSqlDB
import com.vadym.gvd.bestfriendskotlin.restartActivity

class ConditionAdapter(private val context: Context,
                       private val database: ConditionSqlDB,
                       private val conditionList: List<Condition>,
                       private val onMoveItemTouch: (holder: VH) -> Unit) : RecyclerView.Adapter<ConditionAdapter.VH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_condition, parent, false)
    )

    override fun getItemCount() = conditionList.size

    @SuppressLint("ClickableViewAccessibility", "NewApi")
    override fun onBindViewHolder(holder: VH, position: Int) {
        val singleCondition = conditionList[position]
        holder.apply {
            lider?.text = singleCondition.lider
            conditionText?.text = singleCondition.condition
            val durationValue = context.resources.getQuantityString(R.plurals.days, singleCondition.duration?.toInt()!!, singleCondition.duration!!.toInt())
            duration?.text = String.format(context.resources.getString(R.string.duration_value, durationValue, singleCondition.today))
            pubGoal?.text = if (singleCondition.perGoal?.isNotEmpty()!!) {
                (singleCondition.pubGoal + "\n" + singleCondition.perGoal)
            } else (singleCondition.pubGoal + singleCondition.perGoal)
            mainItem?.setOnClickListener {
                contextualMenu.visibility = View.VISIBLE
                title.setTextColor(Color.LTGRAY)
                lider.setTextColor(Color.LTGRAY)
                duration.setTextColor(Color.LTGRAY)
                conditionText.setTextColor(Color.LTGRAY)
                pubGoal.setTextColor(Color.LTGRAY)
            }

            goBack?.setOnClickListener {
                contextualMenu?.visibility = View.GONE
                title.setTextColor(Color.DKGRAY)
                lider?.setTextColor(Color.DKGRAY)
                duration.setTextColor(Color.DKGRAY)
                conditionText.setTextColor(Color.DKGRAY)
                pubGoal.setTextColor(Color.DKGRAY)
                contextualMenu?.setBackgroundColor(context.resources.getColor(R.color.icon_pressed))

                restartActivity(context)
            }
            deleteItem?.setOnClickListener {
                onDeleteIconClick(singleCondition, context as Activity)
            }
            editItem?.setOnClickListener { editTaskDialog(singleCondition) }

            ivMoveItem?.setOnTouchListener { _, event ->
                if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                    contextualMenu?.background = context.getDrawable(R.drawable.button_green_bordered)
                    onMoveItemTouch(holder)
                }
                return@setOnTouchListener false
            }

//            val intDuration = if (!singleCondition.duration.isNullOrBlank()) {
//                singleCondition.duration?.toInt()
//            } else 0
//            decrementDuration = decrementDurationEachDay().takeIf { decrementDurationEachDay() != 0 } ?: singleCondition.duration?.toInt()!!
//            val rr = decrementDurationEachDay()
//            durationLeftover.text = context.resources.getQuantityString(R.plurals.days, rr, rr)
//            when (intDuration?.minus(1)) {
//                0 -> {
//                    iconStatus.visibility = View.VISIBLE
//                    iconStatus.setOnClickListener {
//                        Toast.makeText(context, "Завтра последний день условия", Toast.LENGTH_SHORT).show()
//                    }
//                }
//                -1 -> {
//                    iconStatus.visibility = View.VISIBLE
//                    iconStatus.setImageDrawable(context.resources.getDrawable(R.drawable.ic_delete))
//                    iconStatus.setOnClickListener {
//                        onDeleteIconClick(singleCondition, context as Activity)
//                    }
//                    title.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
//                }
//                else -> iconStatus.visibility = View.GONE
//            }

        }
    }

    private fun onDeleteIconClick(singleCondition: Condition, context: Activity) {
        database.deleteCondition(singleCondition.conditionId)
        restartActivity(context)
    }

    private fun editTaskDialog(condition: Condition) {
        val subView = LayoutInflater.from(context).inflate(R.layout.item_edit_list_condition, null)
        val lider = subView.findViewById<EditText>(R.id.create_lider)
        val duration = subView.findViewById<EditText>(R.id.create_duration)
        val conditionText = subView.findViewById<EditText>(R.id.create_condition)
        val pubGoal = subView.findViewById<EditText>(R.id.create_public_goal)

        lider.setText(condition.lider)
        duration.setText(condition.duration)
        conditionText.setText(condition.condition)
        pubGoal.setText(condition.pubGoal)

        AlertDialog.Builder(context).apply {
            setTitle(R.string.edit_condition)
            setView(subView)
            create()
            setPositiveButton(R.string.edit_condition) { _, _ ->
                val liderFild = lider.text.toString()
                val durationFild = duration.text.toString()
                val conditionFild = conditionText.text.toString()
                val pubGoalFild = pubGoal.text.toString()

                database.updateCondition(Condition(
                        conditionId = condition.conditionId,
                        lider = liderFild,
                        duration = durationFild,
                        today = condition.today.toString(),
                        condition = conditionFild,
                        pubGoal = pubGoalFild,
                        perGoal = "",
                        conditionPosition = condition.conditionPosition))
                if (liderFild.isNotBlank() && durationFild.isNotBlank() && conditionFild.isNotBlank() && pubGoalFild.isNotBlank()) {
                    (this@ConditionAdapter.context as Activity).finish()
                    this@ConditionAdapter.context.startActivity(this@ConditionAdapter.context.intent)
                } else {
                    Toast.makeText(context, R.string.task_cancelled, Toast.LENGTH_SHORT).show()
                }

            }
            setNegativeButton(R.string.cancel) { _, _ -> Toast.makeText(context, R.string.task_cancelled, Toast.LENGTH_SHORT).show() }
            show()
        }

    }

//    private fun decrementDurationEachDay() : Long {
////        var dp = decrementDuration
////        val scheduler = Executors.newSingleThreadScheduledExecutor()
////        scheduler.scheduleAtFixedRate({ dp-- }, 1000, 1, TimeUnit.SECONDS)
//
//        val dp = Observable.timer(1, TimeUnit.DAYS)
//        return dp
//    }


    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.findViewById<TextView>(R.id.tv_title)
        val lider = view.findViewById<TextView>(R.id.tv_lider_value)
        val duration = view.findViewById<TextView>(R.id.tv_duration_value)
        val conditionText = view.findViewById<TextView>(R.id.tv_condition_value)
        val pubGoal = view.findViewById<TextView>(R.id.tv_pub_goal_value)
        val mainItem = view.findViewById<LinearLayout>(R.id.main_item)
        val contextualMenu = view.findViewById<FrameLayout>(R.id.contextual_menu)
//        val iconStatus = view.findViewById<ImageView>(R.id.ic_status)

        val goBack = view.findViewById<ImageView>(R.id.go_back)
        val deleteItem = view.findViewById<ImageView>(R.id.delete_item)
        val editItem = view.findViewById<ImageView>(R.id.edit_item)
        val ivMoveItem = view.findViewById<ImageView>(R.id.iv_move_item)
    }
}