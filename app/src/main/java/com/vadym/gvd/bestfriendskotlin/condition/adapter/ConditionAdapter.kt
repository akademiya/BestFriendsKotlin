package com.vadym.gvd.bestfriendskotlin.condition.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.condition.Condition
import com.vadym.gvd.bestfriendskotlin.condition.database.ConditionSqlDB
import com.vadym.gvd.bestfriendskotlin.restartActivity
import com.vadym.gvd.bestfriendskotlin.shimjeong_shop.CoinManager
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ConditionAdapter(private val context: Context,
                       private val coinManager: CoinManager,
                       private val database: ConditionSqlDB,
                       private val conditionList: List<Condition>,
                       private val onMoveItemTouch: (holder: VH) -> Unit) : RecyclerView.Adapter<ConditionAdapter.VH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_condition, parent, false)
    )

    override fun getItemCount() = conditionList.size

    @SuppressLint("ClickableViewAccessibility", "NewApi", "StringFormatMatches")
    override fun onBindViewHolder(holder: VH, position: Int) {
        val singleCondition = conditionList[position]
        holder.apply {
            lider?.text = singleCondition.lider
            conditionText?.text = singleCondition.condition
            val durationValue = context.resources.getQuantityString(R.plurals.days, singleCondition.duration.toString().toInt(), singleCondition.duration!!.toInt())
            val (finalDate, isFinished) = calculateFinalDay(singleCondition.today.toString(), singleCondition.duration.toString())
            duration?.text = String.format(
                context.resources.getString(
                    R.string.duration_value,
                    durationValue,
                    singleCondition.today,
                    finalDate
                )
            )
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

            if (isFinished) {
                title.setTextColor(Color.RED)
                title.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

                if (!singleCondition.coinsAwarded) {
                    coinManager.addCoins(CoinManager.COINS_FOR_CONDITION)
                    database.markCoinsAwarded(singleCondition.conditionId)
                    Toast.makeText(
                        context,
                        context.getString(R.string.coins_per_condition, CoinManager.COINS_FOR_CONDITION),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

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

    private fun calculateFinalDay(startDay: String, days: String) : Pair<String, Boolean> {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val startDate = LocalDate.parse(startDay, formatter)
        val finalDay = startDate.plusDays(days.toLong()-1)
        val finishedDay = startDate.plusDays(days.toLong())
        val isFinished = finishedDay.isEqual(LocalDate.now()) || finishedDay.isBefore(LocalDate.now())

        return Pair(finalDay.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), isFinished)
    }


    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.findViewById<TextView>(R.id.tv_title)
        val lider = view.findViewById<TextView>(R.id.tv_lider_value)
        val duration = view.findViewById<TextView>(R.id.tv_duration_value)
        val conditionText = view.findViewById<TextView>(R.id.tv_condition_value)
        val pubGoal = view.findViewById<TextView>(R.id.tv_pub_goal_value)
        val mainItem = view.findViewById<LinearLayout>(R.id.main_item)
        val contextualMenu = view.findViewById<FrameLayout>(R.id.contextual_menu)

        val goBack = view.findViewById<ImageView>(R.id.go_back)
        val deleteItem = view.findViewById<ImageView>(R.id.delete_item)
        val editItem = view.findViewById<ImageView>(R.id.edit_item)
        val ivMoveItem = view.findViewById<ImageView>(R.id.iv_move_item)
    }
}