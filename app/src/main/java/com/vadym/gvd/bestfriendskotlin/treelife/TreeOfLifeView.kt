package com.vadym.gvd.bestfriendskotlin.treelife

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.shimjeong_shop.CoinManager
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TreeOfLifeView : MainActivity() {

    private lateinit var db: TreeOfLifeDB
    private lateinit var treeImage: ImageView
    private lateinit var stageLabel: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var progressText: TextView
    private lateinit var coinBalanceIv: ImageView
//    private lateinit var coinCount: TextView
    private lateinit var coinManager: CoinManager
    private lateinit var btnProfile: MaterialCardView
    private lateinit var warnBanner: View
    private lateinit var warnText: TextView
    private lateinit var stageDots: StageDotsView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_tree_of_life)

        db = TreeOfLifeDB.getInstance(this)
        coinManager = CoinManager(this)
        ensureTreeRow()

        bindViews()
        setupToolbar()
        setupBackPress()
        renderTree()

        val tree = db.getTree() ?: return
        findViewById<Chip>(R.id.chip_level_2).isEnabled = tree.level >= 2
        findViewById<Chip>(R.id.chip_level_3).isEnabled = tree.level >= 3

        checkHdhRegression()

        treeImage.setOnClickListener { showTasksDialog() }
        btnProfile.setOnClickListener {
            startActivity(Intent(this, TreeProfileView::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        renderTree()
        checkHdhRegression()
    }

    // ─── Bind ────────────────────────────────────────────────────────────────

    private fun bindViews() {
        treeImage    = findViewById(R.id.tree_image)
        stageLabel   = findViewById(R.id.tree_stage_label)
        stageDots = findViewById(R.id.tree_stage_dots)
//        progressBar  = findViewById(R.id.tree_progress_bar)
//        progressText = findViewById(R.id.tree_progress_text)
        coinBalanceIv = findViewById(R.id.coin_balance)
//        coinCount    = findViewById(R.id.tree_coin_count)
        btnProfile   = findViewById(R.id.btn_profile)
        warnBanner   = findViewById(R.id.tree_warn_banner)
        warnText     = findViewById(R.id.tree_warn_text)
    }

    // ─── Render ──────────────────────────────────────────────────────────────

    private fun renderTree() {
        val tree = db.getTree()
        val stage = tree!!.stage.coerceIn(1, 7)
        val stageName = STAGE_NAMES[stage - 1]
        stageDots.setStage(currentStage = stage, total = 7)

        coinBalanceIv.setOnClickListener {
            showBalanceDialog(coinManager.balance)
        }

        treeImage.setImageResource(treeDrawable(stage))
        stageLabel.text = getString(R.string.status_tree_title, stage, stageName)

        val tasks = tasksForStage(stage)
        val done  = tasks.count { it.isCompleted(this, tree) }
//        progressBar.max = tasks.size
//        progressBar.progress = done
//        progressText.text = "$done / ${tasks.size}"

//        coinCount.text = tree.coins.toString()

        // у TreeOfLifeView.kt після renderTree()
    }

    private fun treeDrawable(stage: Int) = when (stage) {
        1 -> R.drawable.tree_stage_1
        2 -> R.drawable.tree_stage_2
        3 -> R.drawable.tree_stage_3
        4 -> R.drawable.tree_stage_4
        5 -> R.drawable.tree_stage_5
        6 -> R.drawable.tree_stage_6
        else -> R.drawable.tree_stage_7
    }


    // ─── Balance SC dialog ────────────────────────────────────────────────────────

    private fun showBalanceDialog(balance: Int) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_balance, null)
        dialogView.findViewById<TextView>(R.id.balance_text).text = "${balance} 심정 Coins"

        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.coins_balance_title))
            .setView(dialogView)
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }

    // ─── Tasks dialog ────────────────────────────────────────────────────────

    private fun showTasksDialog() {
        val tree  = db.getTree() ?: return
        val stage = tree.stage.coerceIn(1, 7)
        val tasks = tasksForStage(stage)

        val view  = layoutInflater.inflate(R.layout.dialog_tree_tasks, null)
        val rv    = view.findViewById<RecyclerView>(R.id.rv_tasks)
        val title = view.findViewById<TextView>(R.id.dialog_task_title)
        val sub   = view.findViewById<TextView>(R.id.dialog_task_subtitle)

        val nextStage = stage + 1
        title.text = if (stage < 7)
            getString(R.string.task_dialog_title, nextStage, STAGE_NAMES[nextStage - 1])
        else
            getString(R.string.task_dialog_hight)
        sub.text = getString(R.string.task_dialog_sub)

        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = TaskAdapter(tasks, this, tree)
        rv.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: android.graphics.Rect, view: View,
                parent: RecyclerView, state: RecyclerView.State
            ) { outRect.bottom = 8 }
        })

        val allDone = tasks.all { it.isCompleted(this, tree) }

        // Кнопка "Перейти" або "Закрити"
        view.findViewById<com.google.android.material.button.MaterialButton>(R.id.dialog_btn_action)
            .apply {
                text = if (allDone && stage < 7) getString(R.string.next_level, nextStage) else getString(R.string.close_dialog)
            }

        val sheet = com.google.android.material.bottomsheet.BottomSheetDialog(this).apply {
            setContentView(view)
            // Розгортаємо одразу на повну висоту без drag
            behavior.state = com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
            behavior.skipCollapsed = true
        }

        view.findViewById<com.google.android.material.button.MaterialButton>(R.id.dialog_btn_action)
            .setOnClickListener {
                if (allDone && stage < 7) advanceStage()
                sheet.dismiss()
            }

        sheet.show()
    }

    private fun advanceStage() {
        val tree = db.getTree()
        if (tree!!.stage < 7) {
            db.updateStage(tree.stage + 1)
            renderTree()
        }
    }

    // ─── HDH regression check ────────────────────────────────────────────────

    private fun checkHdhRegression() {
        val count = hdhCountThisMonth()
        val tree  = db.getTree()
        val stage = db.getTree()!!.stage.coerceIn(1, 7)

        // Показуємо warning якщо хдх < 20 і місяць ще не закінчився
        if (count < 20 && stage > 1) {
            val remaining = 20 - count
            warnBanner.visibility = View.VISIBLE
            warnText.text = getString(R.string.warning_text, count, remaining)
        } else {
            warnBanner.visibility = View.GONE
        }

        // Перевірка регресу: якщо новий місяць і минулий місяць < 20
        val prefs = getSharedPreferences("hdh_calendar", Context.MODE_PRIVATE)
        val lastCheckMonth = prefs.getString("last_regression_check", "") ?: ""
        val currentMonth   = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"))

        if (lastCheckMonth != currentMonth) {
            val prevMonthCount = hdhCountPrevMonth()
            if (prevMonthCount < 20 && tree!!.stage > 1) {
                db.updateStage(tree.stage - 1)
                showRegressionDialog(prevMonthCount)
            }
            prefs.edit().putString("last_regression_check", currentMonth).apply()
            renderTree()
        }
    }

    private fun hdhCountThisMonth(): Int {
        val prefs = getSharedPreferences("hdh_calendar", Context.MODE_PRIVATE)
        val now   = LocalDate.now()
        val prefix = now.format(DateTimeFormatter.ofPattern("yyyy-MM"))
        return prefs.all.keys.count { it.startsWith(prefix) && prefs.getBoolean(it, false) }
    }

    private fun hdhCountPrevMonth(): Int {
        val prefs     = getSharedPreferences("hdh_calendar", Context.MODE_PRIVATE)
        val prevMonth = LocalDate.now().minusMonths(1)
        val prefix    = prevMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"))
        return prefs.all.keys.count { it.startsWith(prefix) && prefs.getBoolean(it, false) }
    }

    private fun showRegressionDialog(count: Int) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.regress_dialog_title))
            .setMessage(getString(R.string.regress_dialog_message, count))
            .setPositiveButton(getString(R.string.regress_accept)) { _, _ -> }
            .show()
    }

    // ─── DB init ─────────────────────────────────────────────────────────────

    private fun ensureTreeRow() {
        if (db.getTree() == null) db.insertDefaultTree()
    }

    // ─── Navigation ──────────────────────────────────────────────────────────

    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { navigateBack() }
    }

    private fun setupBackPress() {
        onBackPressedDispatcher.addCallback(this) { navigateBack() }
    }

    private fun navigateBack() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra(MainActivity.EXTRA_OPEN_DRAWER, true)
        }
        startActivity(intent)
        finish()
    }

    // ─── Constants ───────────────────────────────────────────────────────────

    companion object {
        val STAGE_NAMES = listOf(
            "Шукач", "Початківець", "Учень",
            "Вірний", "Посвячений", "Служитель", "Лідер 심정"
        )

        fun tasksForStage(stage: Int): List<TreeTask> = when (stage) {
            1 -> listOf(
                HdhWeeklyTask(weeksRequired = 2),
                ReadArticleTask("Традиції -> Молитва")
            )
            2 -> listOf(
                HdhWeeklyTask(weeksRequired = 3),
                QuizTask("Традиції -> Молитва"),
                PrayerTask(minutesPerSession = 12, sessionsRequired = 5)
            )
            3 -> listOf(
                HdhMonthlyTask(countRequired = 20),
                QuizTask("Традиції"),
                CardOpenTask(cardsRequired = 2, scCost = 75)
            )
            4 -> listOf(
                HdhMonthlyTask(countRequired = 20),
                QuizTask("Святі дні"),
                DedicationTask
            )
            5 -> listOf(
                HdhMonthlyTask(countRequired = 20),
                CardOpenTask(cardsRequired = 4, scCost = 75),
                PrayerTask(minutesPerSession = 12, sessionsRequired = 10)
            )
            6 -> listOf(
                HdhMonthlyTask(countRequired = 20),
                QuizTask("Традиції -> Молитва"),
                QuizTask("Традиції"),
                QuizTask("Святі дні"),
                DedicationTask
            )
            else -> emptyList()
        }
    }
}