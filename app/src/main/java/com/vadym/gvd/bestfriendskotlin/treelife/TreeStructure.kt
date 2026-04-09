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
    val individual = listOf(
        "Шукач", "Початківець", "Учень",
        "Вірний", "Посвячений", "Служитель", "Лідер 심정"
    )
    val family = listOf(
        "Іскра життя", "Розквіт любові", "Друге покоління",
        "Міцний фундамент", "Щедрість", "Повнота життя", "Батьківський 심정"
    )
    val generational = listOf(
        "Початок роду", "Закладення традицій", "Сильний рід",
        "Розквіт поколінь", "Спадщина", "Легенда роду", "Божий 심정"
    )

    fun forLevel(level: TreeLevel) = when (level) {
        TreeLevel.INDIVIDUAL   -> individual
        TreeLevel.FAMILY       -> family
        TreeLevel.GENERATIONAL -> generational
    }
}

// ─── Завдання по рівнях і стадіях ────────────────────────────────────────────

object TreeTasksConfig {

    fun tasksFor(level: TreeLevel, stage: Int, ctx: Context): List<TreeTask> = when (level) {
        TreeLevel.INDIVIDUAL   -> individualTasks(stage, ctx)
        TreeLevel.FAMILY       -> familyTasks(stage)
        TreeLevel.GENERATIONAL -> generationalTasks(stage)
    }

    // ── Individual ────────────────────────────────────────────────────────────

    private fun individualTasks(stage: Int, ctx: Context): List<TreeTask> = when (stage) {
        1 -> listOf(
            HdhWeeklyTask(weeksRequired = 1),
            ReadArticleTask(ctx.getString(R.string.pledge)),
            QuizTask(ctx.getString(R.string.pledge)),
            PrayerTask(minutesPerSession = 3, sessionsRequired = 7),
            PhraseOpenTask(3)
        )
        2 -> listOf(
            HdhWeeklyTask(weeksRequired = 2),
            ReadArticleTask(ctx.getString(R.string.hdh)),
            QuizTask(ctx.getString(R.string.hdh)),
            PrayerTask(minutesPerSession = 7, sessionsRequired = 4),
        )
        3 -> listOf(
            HdhWeeklyTask(weeksRequired = 2),
            ReadArticleTask(ctx.getString(R.string.sunday_service)),
            QuizTask(ctx.getString(R.string.sunday_service)),
            CardOpenTask(cardsRequired = 1, scCost = 25)
        )
        4 -> listOf(
            ReadArticleTask(ctx.getString(R.string.anshiil)),
            QuizTask(ctx.getString(R.string.anshiil)),
            DedicationTask,
            CardOpenTask(cardsRequired = 1, scCost = 40)
        )
        5 -> listOf(
            HdhWeeklyTask(weeksRequired = 2),
            PrayerTask(minutesPerSession = 12, sessionsRequired = 3),
            ReadArticleTask(ctx.getString(R.string.life_service)),
            QuizTask(ctx.getString(R.string.life_service))
        )
        6 -> listOf(
            HdhWeeklyTask(weeksRequired = 2),
            ReadArticleTask(ctx.getString(R.string.prayer_tradition)),
            QuizTask(ctx.getString(R.string.prayer_tradition)),
            PhraseOpenTask(5)
        )
        7 -> listOf(
            HdhMonthlyTask(countRequired = 20),
            DedicationTask,
            CardOpenTask(cardsRequired = 1, scCost = 75),
            PrayerTask(minutesPerSession = 12, sessionsRequired = 20),
            ReadArticleTask(ctx.getString(R.string.salt)),
            QuizTask(ctx.getString(R.string.salt)),
            PhraseOpenTask(5)
        )
        else -> emptyList()
    }

    // ── Family ────────────────────────────────────────────────────────────────

    private fun familyTasks(stage: Int): List<TreeTask> = when (stage) {
        1 -> listOf(
            HdhWeeklyTask(weeksRequired = 2),
            ReadArticleTask("Сім'я -> Основи"),
            QuizTask("Сім'я -> Основи"),
            PrayerTask(minutesPerSession = 12, sessionsRequired = 3)
        )
        2 -> listOf(
            HdhWeeklyTask(weeksRequired = 3),
            QuizTask("Сім'я -> Основи"),
            PrayerTask(minutesPerSession = 12, sessionsRequired = 5),
            CardOpenTask(cardsRequired = 1, scCost = 75)
        )
        3 -> listOf(
            HdhMonthlyTask(countRequired = 20),
            ReadArticleTask("Сім'я -> Традиції"),
            QuizTask("Сім'я -> Традиції"),
            CardOpenTask(cardsRequired = 2, scCost = 75)
        )
        4 -> listOf(
            HdhMonthlyTask(countRequired = 20),
            QuizTask("Сім'я -> Святі дні"),
            DedicationTask,
            CardOpenTask(cardsRequired = 2, scCost = 75)
        )
        5 -> listOf(
            HdhMonthlyTask(countRequired = 20),
            CardOpenTask(cardsRequired = 4, scCost = 75),
            PrayerTask(minutesPerSession = 12, sessionsRequired = 10),
            QuizTask("Сім'я -> Святі дні")
        )
        6 -> listOf(
            HdhMonthlyTask(countRequired = 20),
            QuizTask("Сім'я -> Основи"),
            QuizTask("Сім'я -> Традиції"),
            QuizTask("Сім'я -> Святі дні")
        )
        7 -> listOf(
            HdhMonthlyTask(countRequired = 20),
            DedicationTask,
            CardOpenTask(cardsRequired = 6, scCost = 75),
            PrayerTask(minutesPerSession = 12, sessionsRequired = 20)
        )
        else -> emptyList()
    }

    // ── Generational ──────────────────────────────────────────────────────────

    private fun generationalTasks(stage: Int): List<TreeTask> = when (stage) {
        1 -> listOf(
            HdhWeeklyTask(weeksRequired = 2),
            ReadArticleTask("Рід -> Основи"),
            QuizTask("Рід -> Основи"),
            PrayerTask(minutesPerSession = 12, sessionsRequired = 3)
        )
        2 -> listOf(
            HdhWeeklyTask(weeksRequired = 3),
            QuizTask("Рід -> Основи"),
            PrayerTask(minutesPerSession = 12, sessionsRequired = 5),
            CardOpenTask(cardsRequired = 1, scCost = 75)
        )
        3 -> listOf(
            HdhMonthlyTask(countRequired = 20),
            ReadArticleTask("Рід -> Традиції"),
            QuizTask("Рід -> Традиції"),
            CardOpenTask(cardsRequired = 2, scCost = 75)
        )
        4 -> listOf(
            HdhMonthlyTask(countRequired = 20),
            QuizTask("Рід -> Спадщина"),
            DedicationTask,
            CardOpenTask(cardsRequired = 2, scCost = 75)
        )
        5 -> listOf(
            HdhMonthlyTask(countRequired = 20),
            CardOpenTask(cardsRequired = 4, scCost = 75),
            PrayerTask(minutesPerSession = 12, sessionsRequired = 10),
            QuizTask("Рід -> Спадщина")
        )
        6 -> listOf(
            HdhMonthlyTask(countRequired = 20),
            QuizTask("Рід -> Основи"),
            QuizTask("Рід -> Традиції"),
            QuizTask("Рід -> Спадщина")
        )
        7 -> listOf(
            HdhMonthlyTask(countRequired = 20),
            DedicationTask,
            CardOpenTask(cardsRequired = 6, scCost = 75),
            PrayerTask(minutesPerSession = 12, sessionsRequired = 20)
        )
        else -> emptyList()
    }
}