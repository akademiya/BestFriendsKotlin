package com.vadym.gvd.bestfriendskotlin.treelife

import android.content.Context
import com.vadym.gvd.bestfriendskotlin.R

// ─── Рівні ───────────────────────────────────────────────────────────────────

enum class TreeLevel(val displayName: String) {
    INDIVIDUAL("Individual"),
    FAMILY("Family"),
    GENERATIONAL("Generational")
}

// ─── Назви стадій по рівнях ──────────────────────────────────────────────────

object TreeStageNames {
    fun forLevel(level: TreeLevel, ctx: Context) = when (level) {
        TreeLevel.INDIVIDUAL   -> listOf(
            ctx.getString(R.string.individual_1),
            ctx.getString(R.string.individual_2),
            ctx.getString(R.string.individual_3),
            ctx.getString(R.string.individual_4),
            ctx.getString(R.string.individual_5),
            ctx.getString(R.string.individual_6),
            ctx.getString(R.string.individual_7)
        )
        TreeLevel.FAMILY       -> listOf(
            ctx.getString(R.string.family_1),
            ctx.getString(R.string.family_2),
            ctx.getString(R.string.family_3),
            ctx.getString(R.string.family_4),
            ctx.getString(R.string.family_5),
            ctx.getString(R.string.family_6),
            ctx.getString(R.string.family_7)
        )
        TreeLevel.GENERATIONAL -> listOf(
            ctx.getString(R.string.generational_1),
            ctx.getString(R.string.generational_2),
            ctx.getString(R.string.generational_3),
            ctx.getString(R.string.generational_4),
            ctx.getString(R.string.generational_5),
            ctx.getString(R.string.generational_6),
            ctx.getString(R.string.generational_7)
        )
    }
}

// ─── Завдання по рівнях і стадіях ────────────────────────────────────────────

object TreeTasksConfig {

    fun tasksFor(level: TreeLevel, stage: Int, ctx: Context): List<TreeTask> = when (level) {
        TreeLevel.INDIVIDUAL   -> individualTasks(stage, ctx)
        TreeLevel.FAMILY       -> familyTasks(stage, ctx)
        TreeLevel.GENERATIONAL -> generationalTasks(stage, ctx)
    }

    // ── Individual ────────────────────────────────────────────────────────────

    private fun individualTasks(stage: Int, ctx: Context): List<TreeTask> = when (stage) {
        1 -> listOf(
            HdhWeeklyTask(weeksRequired = 1, ctx),
            ReadArticleTask(ctx.getString(R.string.pledge), ctx),
            QuizTask(ctx.getString(R.string.pledge), ctx),
            PrayerTask(minutesPerSession = 3, sessionsRequired = 4, ctx), // 3/7
            PhraseOpenTask(3, ctx)
        )
        2 -> listOf(
            HdhWeeklyTask(weeksRequired = 2, ctx),
            ReadArticleTask(ctx.getString(R.string.hdh), ctx),
            QuizTask(ctx.getString(R.string.hdh), ctx),
            PrayerTask(minutesPerSession = 7, sessionsRequired = 2, ctx), // 7/4
        )
        3 -> listOf(
            HdhWeeklyTask(weeksRequired = 2, ctx),
            ReadArticleTask(ctx.getString(R.string.sunday_service), ctx),
            QuizTask(ctx.getString(R.string.sunday_service), ctx),
            ExerciseTask(2, ctx),
            CardOpenTask(cardsRequired = 1, scCost = 40, ctx)
        )
        4 -> listOf(
            ReadArticleTask(ctx.getString(R.string.anshiil), ctx),
            QuizTask(ctx.getString(R.string.anshiil), ctx),
            AnthemListenTask(ctx),
            DedicationTask(ctx)
        )
        5 -> listOf(
            HdhWeeklyTask(weeksRequired = 2, ctx),
            PrayerTask(minutesPerSession = 12, sessionsRequired = 3, ctx),
            ReadArticleTask(ctx.getString(R.string.life_service), ctx),
            QuizTask(ctx.getString(R.string.life_service), ctx),
            CardOpenTask(cardsRequired = 1, scCost = 40, ctx)
        )
        6 -> listOf(
            HdhWeeklyTask(weeksRequired = 2, ctx),
            ReadArticleTask(ctx.getString(R.string.prayer_tradition), ctx),
            QuizTask(ctx.getString(R.string.prayer_tradition), ctx),
            PhraseOpenTask(5, ctx)
        )
        7 -> listOf(
            HdhMonthlyTask(countRequired = 20, ctx),
            DedicationTask(ctx),
            CardOpenTask(cardsRequired = 1, scCost = 75, ctx),
            PrayerTask(minutesPerSession = 12, sessionsRequired = 3, ctx),
            ReadArticleTask(ctx.getString(R.string.salt), ctx),
            QuizTask(ctx.getString(R.string.salt), ctx),
            PhraseOpenTask(5, ctx)
        )
        else -> emptyList()
    }

    // ── Family ────────────────────────────────────────────────────────────────

    private fun familyTasks(stage: Int, ctx: Context): List<TreeTask> = when (stage) {
        1 -> listOf(
            HdhWeeklyTask(weeksRequired = 2, ctx),
            PrayerTask(minutesPerSession = 12, sessionsRequired = 3, ctx)
        )
        2 -> listOf(
            HdhWeeklyTask(weeksRequired = 3, ctx),
            PrayerTask(minutesPerSession = 12, sessionsRequired = 5, ctx)
        )
        3 -> listOf(
            HdhMonthlyTask(countRequired = 20, ctx),
            CardOpenTask(cardsRequired = 2, scCost = 75, ctx)
        )
        4 -> listOf(
            HdhMonthlyTask(countRequired = 20, ctx),
            DedicationTask(ctx)
        )
        5 -> listOf(
            HdhMonthlyTask(countRequired = 20, ctx),
            PrayerTask(minutesPerSession = 12, sessionsRequired = 10, ctx)
        )
        6 -> listOf(
            HdhMonthlyTask(countRequired = 20, ctx),
            CardOpenTask(cardsRequired = 6, scCost = 75, ctx)
        )
        7 -> listOf(
            HdhMonthlyTask(countRequired = 20, ctx),
            DedicationTask(ctx),
            PrayerTask(minutesPerSession = 12, sessionsRequired = 20, ctx)
        )
        else -> emptyList()
    }

    // ── Generational ──────────────────────────────────────────────────────────

    private fun generationalTasks(stage: Int, ctx: Context): List<TreeTask> = when (stage) {
        1 -> listOf(
            HdhWeeklyTask(weeksRequired = 2, ctx),
            CardOpenTask(cardsRequired = 1, scCost = 75, ctx),
            PrayerTask(minutesPerSession = 12, sessionsRequired = 3, ctx)
        )
        2 -> listOf(
            HdhWeeklyTask(weeksRequired = 3, ctx),
            PrayerTask(minutesPerSession = 12, sessionsRequired = 5, ctx),

        )
        3 -> listOf(
            HdhMonthlyTask(countRequired = 20, ctx),
            CardOpenTask(cardsRequired = 2, scCost = 75, ctx)
        )
        4 -> listOf(
            HdhMonthlyTask(countRequired = 20, ctx),
            DedicationTask(ctx)
        )
        5 -> listOf(
            HdhMonthlyTask(countRequired = 20, ctx),
            CardOpenTask(cardsRequired = 4, scCost = 75, ctx),
            PrayerTask(minutesPerSession = 12, sessionsRequired = 10, ctx)
        )
        6 -> listOf(
            HdhMonthlyTask(countRequired = 20, ctx),
        )
        7 -> listOf(
            HdhMonthlyTask(countRequired = 20, ctx),
            DedicationTask(ctx),
            CardOpenTask(cardsRequired = 6, scCost = 75, ctx),
            PrayerTask(minutesPerSession = 12, sessionsRequired = 20, ctx)
        )
        else -> emptyList()
    }
}