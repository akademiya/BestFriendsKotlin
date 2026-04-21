package com.vadym.gvd.bestfriendskotlin.treelife

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vadym.gvd.bestfriendskotlin.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TaskAdapter(
    private val tasks: List<TreeTask>,
    private val ctx: Context,
    private val tree: TreeRow
) : RecyclerView.Adapter<TaskAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val checkIcon: View    = view.findViewById(R.id.task_check_icon)
        val label: TextView    = view.findViewById(R.id.task_label)
        val badge: TextView    = view.findViewById(R.id.task_badge)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(ctx).inflate(R.layout.item_tree_task, parent, false))

    override fun getItemCount() = tasks.size

    override fun onBindViewHolder(h: VH, pos: Int) {
        val task = tasks[pos]
        val done = task.isCompleted(ctx, tree)

        h.label.text = task.label

        // Strike-through + muted color якщо виконано
        h.label.paintFlags = if (done)
            h.label.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
        else
            h.label.paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()

        h.label.alpha = if (done) 0.5f else 1f

        // Check icon — зелений якщо done
        h.checkIcon.setBackgroundResource(
            if (done) R.drawable.ic_check_done else R.drawable.ic_check_empty
        )

        // Badge з прогресом
        val progress = progressFor(task)
        if (progress != null) {
            h.badge.visibility = View.VISIBLE
            h.badge.text = progress
            h.badge.setBackgroundResource(
                if (done) R.drawable.bg_badge_green else R.drawable.bg_badge_amber
            )
        } else {
            h.badge.visibility = View.GONE
        }
    }

    private fun progressFor(task: TreeTask): String? = when (task) {
        is HdhMonthlyTask -> {
            val prefs  = ctx.getSharedPreferences("hdh_calendar", Context.MODE_PRIVATE)
            val prefix = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"))
            val count  = prefs.all.keys.count { it.startsWith(prefix) && prefs.getBoolean(it, false) }
            "$count/${task.countRequired}"
        }
        is HdhWeeklyTask -> {
            val prefs = ctx.getSharedPreferences("hdh_calendar", Context.MODE_PRIVATE)
            val today = LocalDate.now()
            var q = 0
            for (w in 0 until task.weeksRequired) {
                val start = today.minusWeeks(w.toLong()).with(java.time.DayOfWeek.MONDAY)
                val c = (0..6).count { d ->
                    val key = start.plusDays(d.toLong()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    prefs.getBoolean(key, false)
                }
                if (c >= 4) q++
            }
            "$q/${task.weeksRequired} week"
        }
        is PrayerTask -> {
            val prefs = ctx.getSharedPreferences("prayer_sessions", Context.MODE_PRIVATE)
            val done  = prefs.getInt("sessions_${task.minutesPerSession}min", 0)
            "$done/${task.sessionsRequired}"
        }
//        is CardOpenTask -> {
//            val prefs = ctx.getSharedPreferences("cards_opened", Context.MODE_PRIVATE)
//            val done  = prefs.getInt("cards_${task.scCost}sc", 0)
//            "$done/${task.cardsRequired}"
//        }
        is CardOpenTask -> {
            val done = task.purchasedCount(ctx)
            "$done/${task.cardsRequired}"
        }
        is PhraseOpenTask -> {
            val done = ctx.getSharedPreferences("PhraseForDay", Context.MODE_PRIVATE)
                .getInt("total_phrases_opened", 0)
            "$done/${task.count}"
        }
        else -> null
    }
}