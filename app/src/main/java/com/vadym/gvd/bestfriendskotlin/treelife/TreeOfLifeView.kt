package com.vadym.gvd.bestfriendskotlin.treelife

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.shimjeong_shop.CoinManager
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TreeOfLifeView : MainActivity() {

    private lateinit var db: TreeOfLifeDB
    private lateinit var treeImage: ImageView
    private lateinit var stageLabel: TextView
    private lateinit var coinManager: CoinManager
    private lateinit var btnProfile: MaterialCardView
    private lateinit var warnIcon: ImageView
    private lateinit var stageDots: StageDotsView
    private lateinit var chipGroup: ChipGroup

    // Поточний активний рівень (змінюється при кліку на chip)
    private var activeLevel: TreeLevel = TreeLevel.INDIVIDUAL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_tree_of_life)

        db = TreeOfLifeDB.getInstance(this)
        coinManager = CoinManager(this)
        ensureTreeRow()

        bindViews()
        setupToolbar()
        setupBackPress()
        setupLevelChips()
        renderTree()
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
        treeImage     = findViewById(R.id.tree_image)
        stageLabel    = findViewById(R.id.tree_stage_label)
        stageDots     = findViewById(R.id.tree_stage_dots)
        btnProfile    = findViewById(R.id.btn_profile)
        warnIcon    = findViewById(R.id.tree_warn_icon)
        chipGroup     = findViewById(R.id.level_chip_group)
    }

    // ─── Level chips ─────────────────────────────────────────────────────────

    private fun setupLevelChips() {
        val tree = db.getTree() ?: return

        // Розблоковуємо chips залежно від рівня в БД
        findViewById<Chip>(R.id.chip_level_2).isEnabled = tree.level >= 2
        findViewById<Chip>(R.id.chip_level_3).isEnabled = tree.level >= 3

        chipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            activeLevel = when (checkedIds.firstOrNull()) {
                R.id.chip_level_2 -> TreeLevel.FAMILY
                R.id.chip_level_3 -> TreeLevel.GENERATIONAL
                else              -> TreeLevel.INDIVIDUAL
            }
            getSharedPreferences("tree_ui", Context.MODE_PRIVATE)
                .edit().putString("active_level", activeLevel.name).apply()
            renderTree()
        }
    }

    // ─── Render ──────────────────────────────────────────────────────────────

    private fun renderTree() {
        val tree  = db.getTree() ?: return
        val stage = tree.stageForLevel(activeLevel).coerceIn(1, 7)
        val stageNames = TreeStageNames.forLevel(activeLevel, this)
        val stageName  = stageNames[stage - 1]

        stageDots.setStage(currentStage = stage, total = 7)
        treeImage.setImageResource(treeDrawable(stage))
        stageLabel.text = getString(R.string.status_tree_title, stage, stageName)

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

    private fun treeRegressDrawable(level: TreeLevel, stage: Int) = when (level) {
        TreeLevel.INDIVIDUAL -> when (stage) {
            1    -> R.drawable.tree_stage_1
            2    -> R.drawable.tree_regres
            3, 4 -> R.drawable.tree4_regres
            5    -> R.drawable.tree4_regres
            6    -> R.drawable.tree6_regres
            else -> R.drawable.tree7_regres
        }
        // Family і Generational — підстав свої drawable або використай ті самі
        TreeLevel.FAMILY, TreeLevel.GENERATIONAL -> when (stage) {
            1    -> R.drawable.tree_stage_1
            2    -> R.drawable.tree_regres
            3, 4 -> R.drawable.tree4_regres
            5    -> R.drawable.tree4_regres
            6    -> R.drawable.tree6_regres
            else -> R.drawable.tree7_regres
        }
    }


    // ─── Tasks dialog ─────────────────────────────────────────────────────────

    private fun showTasksDialog() {
        val tree  = db.getTree() ?: return
        val stage = tree.stageForLevel(activeLevel).coerceIn(1, 7)
        val tasks = TreeTasksConfig.tasksFor(activeLevel, stage, this)
        val stageNames = TreeStageNames.forLevel(activeLevel, this)

        val view  = layoutInflater.inflate(R.layout.dialog_tree_tasks, null)
        val rv    = view.findViewById<RecyclerView>(R.id.rv_tasks)
        val title = view.findViewById<TextView>(R.id.dialog_task_title)
        val sub   = view.findViewById<TextView>(R.id.dialog_task_subtitle)

        val nextStage = (stage + 1).coerceAtMost(7)
        title.text = if (stage < 7)
            getString(R.string.task_dialog_title, nextStage, stageNames[nextStage - 1])
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
        view.findViewById<MaterialButton>(R.id.dialog_btn_action)
            .apply {
                text = if (allDone && stage < 7)
                    getString(R.string.next_level, nextStage)
                else
                    getString(R.string.close_dialog)
            }

        val sheet = BottomSheetDialog(this, R.style.TransparentBottomSheet).apply {
            setContentView(view)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.skipCollapsed = true
        }

        view.findViewById<MaterialButton>(R.id.dialog_btn_action)
            .setOnClickListener {
                if (allDone && stage < 7) advanceStage()
                sheet.dismiss()
            }

        sheet.show()
    }

    private fun advanceStage() {
        val tree = db.getTree() ?: return
        val current = tree.stageForLevel(activeLevel)
        if (current < 7) {
            db.updateStageForLevel(activeLevel, current + 1)
            renderTree()
        }
    }

    // ─── HDH regression ──────────────────────────────────────────────────────

    private fun checkHdhRegression() {
        val count = hdhCountThisMonth()
        val tree  = db.getTree() ?: return
        val stage = tree.stageForLevel(activeLevel).coerceIn(1, 7)

        if (count in 9..16 && stage > 1) {
            val root = findViewById<View>(android.R.id.content)
            warnIcon.visibility = View.VISIBLE
            warnIcon.setOnClickListener {
                Snackbar.make(root, getString(R.string.warning_text, count, 16 - count), Snackbar.LENGTH_LONG).show()
            }
        } else {
            warnIcon.visibility = View.GONE
        }

        val prefs        = getSharedPreferences("hdh_calendar", Context.MODE_PRIVATE)
        val lastCheck    = prefs.getString("last_regression_check", "") ?: ""
        val currentMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"))

        if (lastCheck != currentMonth) {
            val prev = hdhCountPrevMonth()
            var didRegress = false
            // Регрес діє на всі рівні одночасно
            TreeLevel.entries.forEach { level ->
                val s = tree.stageForLevel(level)
                if (prev < 16 && s > 1) {
                    db.updateStageForLevel(level, s - 1)
                    didRegress = true
                }
            }
//            if (prev < 16) {
//                treeImage.setImageResource(treeRegressDrawable(stage))
//                showRegressionDialog(prev)
//            }
            prefs.edit().putString("last_regression_check", currentMonth).apply()
            renderTree()

            if (didRegress) {
                val newStage = db.getTree()?.stageForLevel(activeLevel)?.coerceIn(1, 7) ?: 1
                treeImage.setImageResource(treeRegressDrawable(activeLevel, newStage))
                showRegressionDialog(prev)
            }
        }
    }

    private fun hdhCountThisMonth(): Int {
        val prefs  = getSharedPreferences("hdh_calendar", Context.MODE_PRIVATE)
        val prefix = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"))
        return prefs.all.keys.count { it.startsWith(prefix) && prefs.getBoolean(it, false) }
    }

    private fun hdhCountPrevMonth(): Int {
        val prefs  = getSharedPreferences("hdh_calendar", Context.MODE_PRIVATE)
        val prefix = LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM"))
        return prefs.all.keys.count { it.startsWith(prefix) && prefs.getBoolean(it, false) }
    }

    private fun showRegressionDialog(count: Int) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.regress_dialog_title))
            .setMessage(getString(R.string.regress_dialog_message, count))
            .setPositiveButton(getString(R.string.regress_accept)) { _, _ -> }
            .show()
    }

    // ─── Helpers ─────────────────────────────────────────────────────────────

    private fun ensureTreeRow() {
        if (db.getTree() == null) db.insertDefaultTree()
    }

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
}