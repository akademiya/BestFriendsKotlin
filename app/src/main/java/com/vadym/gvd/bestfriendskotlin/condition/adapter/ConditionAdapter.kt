package com.vadym.gvd.bestfriendskotlin.condition.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.condition.Condition
import com.vadym.gvd.bestfriendskotlin.condition.database.ConditionSqlDB

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
            duration?.text = singleCondition.duration
            pubGoal?.text = singleCondition.pubGoal
            perGoal?.text = singleCondition.perGoal
            mainItem?.setOnClickListener {
                contextualMenu.visibility = View.VISIBLE
                lider.setTextColor(Color.LTGRAY)
                duration.setTextColor(Color.LTGRAY)
                conditionText.setTextColor(Color.LTGRAY)
                pubGoal.setTextColor(Color.LTGRAY)
                perGoal.setTextColor(Color.LTGRAY)
            }

            goBack?.setOnClickListener {
                contextualMenu?.visibility = View.GONE
                lider?.setTextColor(Color.DKGRAY)
                duration.setTextColor(Color.DKGRAY)
                conditionText.setTextColor(Color.DKGRAY)
                pubGoal.setTextColor(Color.DKGRAY)
                perGoal.setTextColor(Color.DKGRAY)
                contextualMenu?.setBackgroundColor(context.resources.getColor(R.color.icon_pressed))
                // FIXME исправить этот костыль
                (context as Activity).finish()
                context.startActivity(context.intent)
            }
            deleteItem?.setOnClickListener {
                database.deleteCondition(singleCondition.conditionId)
                (context as Activity).finish()
                context.startActivity(context.intent)
            }
            editItem?.setOnClickListener { editTaskDialog(singleCondition) }

            ivMoveItem?.setOnTouchListener { v, event ->
                if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                    contextualMenu?.background = context.getDrawable(R.drawable.button_green_bordered)
                    onMoveItemTouch(holder)
                }
                return@setOnTouchListener false
            }

        }
    }

    private fun editTaskDialog(condition: Condition) {
        val subView = LayoutInflater.from(context).inflate(R.layout.item_edit_list_condition, null)
        val lider = subView.findViewById<EditText>(R.id.create_lider)
        val duration = subView.findViewById<EditText>(R.id.create_duration)
        val conditionText = subView.findViewById<EditText>(R.id.create_condition)
        val pubGoal = subView.findViewById<EditText>(R.id.create_public_goal)
        val perGoal = subView.findViewById<EditText>(R.id.create_personal_goal)

        lider.setText(condition.lider)
        duration.setText(condition.duration)
        conditionText.setText(condition.condition)
        pubGoal.setText(condition.pubGoal)
        perGoal.setText(condition.perGoal)

        AlertDialog.Builder(context).apply {
            setTitle(R.string.edit_condition)
            setView(subView)
            create()
            setPositiveButton(R.string.edit_condition) { _, _ ->
                val liderFild = lider.text.toString()
                val durationFild = duration.text.toString()
                val conditionFild = conditionText.text.toString()
                val pubGoalFild = pubGoal.text.toString()
                val perGoalFild = perGoal.text.toString()

                database.updateCondition(Condition(
                        conditionId = condition.conditionId,
                        lider = lider.text.toString(),
                        duration = duration.text.toString(),
                        condition = conditionText.text.toString(),
                        pubGoal = pubGoal.text.toString(),
                        perGoal = perGoal.text.toString(),
                        conditionPosition = condition.conditionPosition))
                (this@ConditionAdapter.context as Activity).finish()
                this@ConditionAdapter.context.startActivity(this@ConditionAdapter.context.intent)
            }
            setNegativeButton(R.string.cancel) { _, _ -> Toast.makeText(context, R.string.task_cancelled, Toast.LENGTH_SHORT).show() }
            show()
        }

    }


    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val lider = view.findViewById<TextView>(R.id.tv_lider_value)
        val duration = view.findViewById<TextView>(R.id.tv_duration_value)
        val conditionText = view.findViewById<TextView>(R.id.tv_condition_value)
        val pubGoal = view.findViewById<TextView>(R.id.tv_pub_goal_value)
        val perGoal = view.findViewById<TextView>(R.id.tv_per_goal_value)
        val mainItem = view.findViewById<LinearLayout>(R.id.main_item)
        val contextualMenu = view.findViewById<FrameLayout>(R.id.contextual_menu)

        val goBack = view.findViewById<ImageView>(R.id.go_back)
        val deleteItem = view.findViewById<ImageView>(R.id.delete_item)
        val editItem = view.findViewById<ImageView>(R.id.edit_item)
        val ivMoveItem = view.findViewById<ImageView>(R.id.iv_move_item)
    }
}