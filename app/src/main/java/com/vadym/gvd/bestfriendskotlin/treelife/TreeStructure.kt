package com.vadym.gvd.bestfriendskotlin.treelife

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

    fun tasksFor(level: TreeLevel, stage: Int): List<TreeTask> = when (level) {
        TreeLevel.INDIVIDUAL   -> individualTasks(stage)
        TreeLevel.FAMILY       -> familyTasks(stage)
        TreeLevel.GENERATIONAL -> generationalTasks(stage)
    }

    // ── Individual ────────────────────────────────────────────────────────────

    private fun individualTasks(stage: Int): List<TreeTask> = when (stage) {
        1 -> listOf(
            HdhWeeklyTask(weeksRequired = 2),
            ReadArticleTask("Традиції -> Молитва"),
            QuizTask("Традиції -> Молитва"),
            PrayerTask(minutesPerSession = 12, sessionsRequired = 3)
        )
        2 -> listOf(
            HdhWeeklyTask(weeksRequired = 3),
            QuizTask("Традиції -> Молитва"),
            PrayerTask(minutesPerSession = 12, sessionsRequired = 5),
            CardOpenTask(cardsRequired = 1, scCost = 75)
        )
        3 -> listOf(
            HdhMonthlyTask(countRequired = 20),
            QuizTask("Традиції"),
            CardOpenTask(cardsRequired = 2, scCost = 75),
            ReadArticleTask("Традиції")
        )
        4 -> listOf(
            HdhMonthlyTask(countRequired = 20),
            QuizTask("Святі дні"),
            DedicationTask,
            CardOpenTask(cardsRequired = 2, scCost = 75)
        )
        5 -> listOf(
            HdhMonthlyTask(countRequired = 20),
            CardOpenTask(cardsRequired = 4, scCost = 75),
            PrayerTask(minutesPerSession = 12, sessionsRequired = 10),
            QuizTask("Святі дні")
        )
        6 -> listOf(
            HdhMonthlyTask(countRequired = 20),
            QuizTask("Традиції -> Молитва"),
            QuizTask("Традиції"),
            QuizTask("Святі дні"),
        )
        7 -> listOf(
            HdhMonthlyTask(countRequired = 20),
            DedicationTask,
            CardOpenTask(cardsRequired = 6, scCost = 75),
            PrayerTask(minutesPerSession = 12, sessionsRequired = 20)
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