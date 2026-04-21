package com.vadym.gvd.bestfriendskotlin.treelife

import android.content.Context
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.condition.database.ConditionSqlDB
import com.vadym.gvd.bestfriendskotlin.shimjeong_shop.ShopCard
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// ─── Base ────────────────────────────────────────────────────────────────────

sealed class TreeTask {
    abstract val label: String
    abstract fun isCompleted(ctx: Context, tree: TreeRow): Boolean
    open val progressText: String? get() = null // напр. "2/5"
}

// ─── ХДХ 4р/тиждень × N тижнів ──────────────────────────────────────────────

data class HdhWeeklyTask(val weeksRequired: Int, val ctx: Context) : TreeTask() {
    override val label get() = ctx.getString(R.string.task_hdh_weekly, weeksRequired)

    override fun isCompleted(ctx: Context, tree: TreeRow): Boolean {
        val prefs = ctx.getSharedPreferences("hdh_calendar", Context.MODE_PRIVATE)

        // Беремо дату відкриття поточної стадії для активного рівня
        // Якщо дата невідома (стадія 1 з початку) — беремо сьогодні
        val stageOpenDate = tree.stageDateForLevel(activeLevel(ctx, tree))
            ?: LocalDate.now()

        // Початок тижня в якому відкрилась стадія
        val stageWeekStart = stageOpenDate.with(java.time.DayOfWeek.MONDAY)

        var qualifyingWeeks = 0

        for (weekOffset in 0 until weeksRequired) {
            val weekStart = stageWeekStart.plusWeeks(weekOffset.toLong())
            // Не рахуємо тижні у майбутньому
            if (weekStart.isAfter(LocalDate.now())) break

            val count = (0..6).count { dayOffset ->
                val day = weekStart.plusDays(dayOffset.toLong())
                // Не рахуємо майбутні дні
                if (day.isAfter(LocalDate.now())) return@count false
                val key = day.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                prefs.getBoolean(key, false)
            }
            if (count >= 4) qualifyingWeeks++
        }
        return qualifyingWeeks >= weeksRequired
    }

    // Визначаємо активний рівень через SharedPreferences
    // (зберігається при перемиканні chip у TreeOfLifeView)
    private fun activeLevel(ctx: Context, tree: TreeRow): TreeLevel {
        val saved = ctx.getSharedPreferences("tree_ui", Context.MODE_PRIVATE)
            .getString("active_level", TreeLevel.INDIVIDUAL.name) ?: TreeLevel.INDIVIDUAL.name
        return runCatching { TreeLevel.valueOf(saved) }.getOrDefault(TreeLevel.INDIVIDUAL)
    }


//    override val label get() = ctx.getString(R.string.task_hdh_weekly, weeksRequired) //"ХДХ 4р/тиждень × $weeksRequired тижні"
//
//    override fun isCompleted(ctx: Context, tree: TreeRow): Boolean {
//        val prefs = ctx.getSharedPreferences("hdh_calendar", Context.MODE_PRIVATE)
//        var qualifyingWeeks = 0
//        val today = LocalDate.now()
//
//        // Перевіряємо останні N тижнів назад від сьогодні
//        for (weekOffset in 0 until weeksRequired) {
//            val weekStart = today.minusWeeks(weekOffset.toLong()).with(java.time.DayOfWeek.MONDAY)
//            val count = (0..6).count { dayOffset ->
//                val day = weekStart.plusDays(dayOffset.toLong())
//                val key = day.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
//                prefs.getBoolean(key, false)
//            }
//            if (count >= 4) qualifyingWeeks++
//        }
//        return qualifyingWeeks >= weeksRequired
//    }
}

// ─── ХДХ 20+ за місяць ───────────────────────────────────────────────────────

data class HdhMonthlyTask(val countRequired: Int = 20, val ctx: Context) : TreeTask() {
    override val label get() = ctx.getString(R.string.task_hdh_monthly, countRequired) //"ХДХ $countRequired+ разів цього місяця"

    override fun isCompleted(ctx: Context, tree: TreeRow): Boolean {
        val prefs  = ctx.getSharedPreferences("hdh_calendar", Context.MODE_PRIVATE)
        val prefix = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"))
        val count  = prefs.all.keys.count { it.startsWith(prefix) && prefs.getBoolean(it, false) }
        return count >= countRequired
    }

    override val progressText: String
        get() = "" // заповнюється динамічно в адаптері
}

// ─── Молитва N хвилин × K сесій ──────────────────────────────────────────────

data class PrayerTask(
    val minutesPerSession: Int,
    val sessionsRequired: Int,
    val ctx: Context
) : TreeTask() {
    override val label get() = ctx.getString(R.string.task_prayer, minutesPerSession, sessionsRequired) //"Молитва $minutesPerSession хв × $sessionsRequired разів"

    override fun isCompleted(ctx: Context, tree: TreeRow): Boolean {
        val prefs = ctx.getSharedPreferences("prayer_sessions", Context.MODE_PRIVATE)
        val done  = prefs.getInt("sessions_${minutesPerSession}min", 0)
        return done >= sessionsRequired
    }

    override val progressText: String
        get() = "" // заповнюється динамічно в адаптері
}

// ─── Прочитати статтю + клікнути монету ──────────────────────────────────────

data class ReadArticleTask(val articleKey: String, val ctx: Context) : TreeTask() {
    override val label get() = ctx.getString(R.string.task_read_article, articleKey) //"Прочитати «$articleKey»"

    override fun isCompleted(ctx: Context, tree: TreeRow): Boolean {
        val prefs = ctx.getSharedPreferences("articles_read", Context.MODE_PRIVATE)
        return prefs.getBoolean("read_$articleKey", false)
    }
}

// ─── Вікторина ───────────────────────────────────────────────────────────────

data class QuizTask(val quizKey: String, val ctx: Context) : TreeTask() {
    override val label get() = ctx.getString(R.string.task_quiz, quizKey) //"Вікторина «$quizKey»"

    override fun isCompleted(ctx: Context, tree: TreeRow): Boolean {
        val prefs = ctx.getSharedPreferences("quizzes_passed", Context.MODE_PRIVATE)
        return prefs.getBoolean("quiz_$quizKey", false)
    }
}

// ─── Відкрити картки × SC ────────────────────────────────────────────────────

data class CardOpenTask(val cardsRequired: Int, val scCost: Int, val ctx: Context) : TreeTask() {
    override val label get() = ctx.getString(R.string.task_open_card, cardsRequired, scCost) //"Відкрити $cardsRequired картки × $scCost SC"

    override fun isCompleted(ctx: Context, tree: TreeRow): Boolean {
        val prefs = ctx.getSharedPreferences("cards_opened", Context.MODE_PRIVATE)
        val done  = prefs.getInt("cards_${scCost}sc", 0)
        return done >= cardsRequired
    }

    override val progressText: String
        get() = "" // заповнюється динамічно в адаптері

    fun purchasedCount(ctx: Context): Int {
        val purchasedIds = ctx.getSharedPreferences("shimjeong_coins", Context.MODE_PRIVATE)
            .getStringSet("purchased_cards", emptySet()) ?: return 0

        return purchasedIds
            .mapNotNull { it.toIntOrNull() }
            .count { cardId ->
                val card = ShopCard.all.find { it.id == cardId }
                card?.price == scCost
            }
    }
}


// ─── Відкрити фразу на день ────────────────────────────────────────────────────

data class PhraseOpenTask(val count: Int, val ctx: Context) : TreeTask() {
    override val label get() = ctx.getString(R.string.task_open_phrase, count) //"Відкрити «Фраза на день» × $count разів"

    override fun isCompleted(ctx: Context, tree: TreeRow): Boolean {
        val done = ctx.getSharedPreferences("PhraseForDay", Context.MODE_PRIVATE)
            .getInt("total_phrases_opened", 0)
        return done >= count
    }

    override val progressText: String
        get() = "" // заповнюється динамічно в адаптері
}

// ─── Умова посвячення ─────────────────────────────────────────────────────────

data class DedicationTask(val ctx: Context) : TreeTask() {
    override val label = ctx.getString(R.string.condition_title) //"Умова посвячення"

    override fun isCompleted(ctx: Context, tree: TreeRow): Boolean {
        return finishedCount(ctx) > 0
    }

    /**
     * Рахує кількість завершених умов посвячення з ConditionSqlDB.
     * Умова вважається завершеною якщо (startDate + duration) <= сьогодні —
     * та сама логіка що в ConditionAdapter.calculateFinalDay()
     */
    fun finishedCount(ctx: Context): Int {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        return try {
            ConditionSqlDB.getInstance(ctx)
                .listConditions()
                .count { condition ->
                    val startDate  = LocalDate.parse(condition.today.toString(), formatter)
                    val finishDate = startDate.plusDays(condition.duration!!.toLong())
                    finishDate.isEqual(LocalDate.now()) || finishDate.isBefore(LocalDate.now())
                }
        } catch (e: Exception) {
            0
        }
    }
}


// ─── Прослухати гімн ЧІГ ─────────────────────────────────────────────────────────

data class AnthemListenTask(val ctx: Context) : TreeTask() {
    override val label get() = ctx.getString(R.string.task_listen_anthem)

    override fun isCompleted(ctx: Context, tree: TreeRow): Boolean {
        return ctx.getSharedPreferences("anthem_task", Context.MODE_PRIVATE)
            .getBoolean("anthem_listened", false)
    }
}