package com.vadym.gvd.bestfriendskotlin.treelife

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.condition.database.ConditionSqlDB
import com.vadym.gvd.bestfriendskotlin.easter_egg.CoinActivity
import com.vadym.gvd.bestfriendskotlin.shimjeong_shop.CoinManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TreeProfileView : CoinActivity() {

    private lateinit var db: TreeOfLifeDB
    private lateinit var coinManager: CoinManager
    private lateinit var profilePhoto: ImageView
//    private lateinit var editPhotoBadge: View
    private lateinit var nicknameText: TextView
    private lateinit var editNicknameBtn: ImageView
    private lateinit var warnBanner: View
    private lateinit var warnText: TextView
    private lateinit var statsGrid: RecyclerView
    private lateinit var statusChipGroup: ChipGroup
    private lateinit var statusList: LinearLayout

    private val PREFS_PROFILE = "tree_profile"

    // Вибір фото з галереї
    private val pickPhoto = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri: Uri? = result.data?.data
            uri?.let {
                try {
                    contentResolver.takePersistableUriPermission(
                        it, Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                } catch (_: SecurityException) {}
                loadPhoto(it)
                getSharedPreferences(PREFS_PROFILE, MODE_PRIVATE)
                    .edit().putString("photo_uri", it.toString()).apply()
            }
        }
    }

    override val coinViewMap = mapOf(
        "coin_tree_001" to R.id.coin_info_001
//        "coin_tree_002" to R.id.coin_tree_002,
        // ...
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_tree_profile)

        db          = TreeOfLifeDB.getInstance(this)
        coinManager = CoinManager(this)

        bindViews()
        setupToolbar()
        setupBackPress()
        renderProfile()
    }

    // ─── Bind ────────────────────────────────────────────────────────────────

    private fun bindViews() {
        profilePhoto    = findViewById(R.id.profile_photo)
//        editPhotoBadge  = findViewById(R.id.edit_photo_badge)
        nicknameText    = findViewById(R.id.profile_nickname)
        editNicknameBtn = findViewById(R.id.edit_nickname_btn)
        warnBanner      = findViewById(R.id.profile_warn_banner)
        warnText        = findViewById(R.id.profile_warn_text)
        statusChipGroup = findViewById(R.id.profile_level_chips)
        statusList      = findViewById(R.id.profile_status_list)
    }

    // ─── Render ──────────────────────────────────────────────────────────────

    private fun renderProfile() {
        val prefs = getSharedPreferences(PREFS_PROFILE, MODE_PRIVATE)

        // UI без БД — одразу
        val savedUri = prefs.getString("photo_uri", null)
        if (savedUri != null) loadPhoto(Uri.parse(savedUri))
//        editPhotoBadge.setOnClickListener { openGallery() }
        profilePhoto.setOnClickListener { openGallery() }
        nicknameText.text = prefs.getString("nickname", "User") ?: "User"
        editNicknameBtn.setOnClickListener { showEditNicknameDialog() }

        // LiveData observe — один раз тут
        coinViewModel.collectedCount.observe(this) { _ ->
            lifecycleScope.launch {
                val tree = withContext(Dispatchers.IO) { db.getTree() } ?: return@launch
                val hdh  = withContext(Dispatchers.IO) { hdhCountThisMonth() }
                val ded  = withContext(Dispatchers.IO) { DedicationTask(this@TreeProfileView).finishedCount(this@TreeProfileView) }
                val cond = withContext(Dispatchers.IO) { ConditionSqlDB.getInstance(this@TreeProfileView).listConditions().size }
                renderStatCards(tree, hdh, ded, cond)
            }
        }

        // Основне завантаження
        lifecycleScope.launch {
            val tree = withContext(Dispatchers.IO) { db.getTree() } ?: return@launch
            val hdh  = withContext(Dispatchers.IO) { hdhCountThisMonth() }
            val ded  = withContext(Dispatchers.IO) { DedicationTask(this@TreeProfileView).finishedCount(this@TreeProfileView) }
            val cond = withContext(Dispatchers.IO) { ConditionSqlDB.getInstance(this@TreeProfileView).listConditions().size }

            if (hdh < 16) {
                warnBanner.visibility = View.VISIBLE
                warnText.text = getString(R.string.warning_text_profile, hdh, daysLeftInMonth(), 16 - hdh)
            } else {
                warnBanner.visibility = View.GONE
            }

            renderStatCards(tree, hdh, ded, cond)
            setupLevelChips(tree)
            renderStatusList(tree, TreeLevel.INDIVIDUAL)
            renderIndividualAchievement(tree)
        }
    }

    // ─── Stat cards ──────────────────────────────────────────────────────────

    private fun renderStatCards(tree: TreeRow, hdhCount: Int, dedicationCount: Int, totalConditions: Int) {
        val prefs     = getSharedPreferences(PREFS_PROFILE, MODE_PRIVATE)
        val joinDate  = prefs.getString("join_date", LocalDate.now().toString()) ?: LocalDate.now().toString()
        val daysIn    = LocalDate.parse(joinDate).until(LocalDate.now()).days.coerceAtLeast(0)
        val cardsOpened = getSharedPreferences("shimjeong_coins", MODE_PRIVATE)
            .getStringSet("purchased_cards", emptySet())?.size ?: 0
        val phrasesOpened = getSharedPreferences("PhraseForDay", MODE_PRIVATE)
            .getInt("total_phrases_opened", 0)
        val eggCount = coinViewModel.collectedCount.value ?: 0

        val cards = listOf(
            StatCard(hdhCount.toString(), getString(R.string.stat_card_hdh), R.drawable.bg_stat_card),
            StatCard(coinManager.balance.toString(), getString(R.string.stat_card_sc), R.drawable.bg_stat_card_blue),
            StatCard("${tree.stageIndividual} / 7", TreeLevel.INDIVIDUAL.displayName, R.drawable.bg_stat_card_turquoise),
            StatCard("$daysIn", getString(R.string.stat_card_days), R.drawable.bg_stat_card_purple),
            StatCard(cardsOpened.toString(), getString(R.string.stat_card_cards), R.drawable.bg_stat_card_orange),
            StatCard("$dedicationCount / $totalConditions", getString(R.string.stat_card_conditions), R.drawable.bg_stat_card_red),
            StatCard(phrasesOpened.toString(), getString(R.string.stat_card_phrases), R.drawable.bg_stat_card_pink),
            StatCard("$eggCount / 100", getString(R.string.stat_card_easter_egg), R.drawable.bg_stat_card_salad)
        )

        val grid = findViewById<GridLayout>(R.id.stats_grid)
        grid.removeAllViews()
        cards.forEach { card ->
            val v = layoutInflater.inflate(R.layout.item_stat_card, grid, false)
            v.findViewById<TextView>(R.id.stat_value).text = card.value
            v.findViewById<TextView>(R.id.stat_label).text = card.label
            v.setBackgroundResource(card.colorRes)
            grid.addView(v)
        }
    }


    data class StatCard(val value: String, val label: String, val colorRes: Int)

    // ─── Level chips ─────────────────────────────────────────────────────────

    private fun setupLevelChips(tree: TreeRow) {
        findViewById<Chip>(R.id.profile_chip_2).isEnabled = tree.level >= 2
        findViewById<Chip>(R.id.profile_chip_3).isEnabled = tree.level >= 3

        statusChipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            val level = when (checkedIds.firstOrNull()) {
                R.id.profile_chip_2 -> TreeLevel.FAMILY
                R.id.profile_chip_3 -> TreeLevel.GENERATIONAL
                else                -> TreeLevel.INDIVIDUAL
            }
            renderStatusList(tree, level)
        }
    }

    // ─── Status list ─────────────────────────────────────────────────────────

    private fun renderStatusList(tree: TreeRow, level: TreeLevel) {
        val stage      = tree.stageForLevel(level)
        val names      = TreeStageNames.forLevel(level, this)
        statusList.removeAllViews()

        // Поточний статус — виділений
        val currentView = layoutInflater.inflate(R.layout.item_status_current, statusList, false)
        currentView.findViewById<TextView>(R.id.status_name).text  = names[stage - 1]
        currentView.findViewById<TextView>(R.id.status_level).text = getString(R.string.status_level_text, level.displayName, stage)
        statusList.addView(currentView)

        // Всі стадії
        names.forEachIndexed { idx, name ->
            val stageNum = idx + 1
            if (stageNum == stage) return@forEachIndexed  // поточна вже показана

            val itemView = layoutInflater.inflate(R.layout.item_status_locked, statusList, false)
            itemView.findViewById<TextView>(R.id.locked_name).text = name
            itemView.findViewById<TextView>(R.id.locked_req).text  =
                if (stageNum < stage) getString(R.string.status_fulfill) else getString(R.string.current_status_list, stageNum, level.displayName)
            val lockIcon = itemView.findViewById<ImageView>(R.id.lock_icon)
            lockIcon.setImageResource(
                if (stageNum < stage) R.drawable.ic_check_done else R.drawable.ic_lock
            )
            statusList.addView(itemView)
        }
    }

    // ─── Individual achievement ───────────────────────────────────────────────

    private fun renderIndividualAchievement(tree: TreeRow) {
        val achievementCard = findViewById<View>(R.id.individual_achievement_card)
        if (tree.stageIndividual >= 7) {
            achievementCard.visibility = View.VISIBLE
            val prefs    = getSharedPreferences(PREFS_PROFILE, MODE_PRIVATE)
            val dateStr  = prefs.getString("individual_completed_date", LocalDate.now().toString()) ?: LocalDate.now().toString()
            val joinDate = prefs.getString("join_date", LocalDate.now().toString()) ?: LocalDate.now().toString()
            val days     = LocalDate.parse(joinDate).until(LocalDate.parse(dateStr)).days

            // Зберігаємо дату досягнення якщо ще не збережена
            if (!prefs.contains("individual_completed_date")) {
                prefs.edit().putString("individual_completed_date", LocalDate.now().toString()).apply()
            }

            achievementCard.findViewById<TextView>(R.id.achievement_date).text =
                getString(R.string.achieved, LocalDate.parse(dateStr).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
            achievementCard.findViewById<TextView>(R.id.achievement_days).text =
                getString(R.string.achievement_days, days)
        } else {
            achievementCard.visibility = View.GONE
        }
    }

    // ─── Edit nickname ────────────────────────────────────────────────────────

    private fun showEditNicknameDialog() {
        if (coinManager.balance < 5) {
            Toast.makeText(this, getString(R.string.nickname_not_enough), Toast.LENGTH_SHORT).show()
            return
        }
        val input = EditText(this).apply {
            hint = getString(R.string.nickname_new)
            setText(nicknameText.text)
            setPadding(32, 16, 32, 16)
        }
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.nickname_rename))
            .setMessage(getString(R.string.nickname_balance, coinManager.balance))
            .setView(input)
            .setPositiveButton(getString(R.string.nickname_save)) { _, _ ->
                val newName = input.text.toString().trim()
                if (newName.isNotEmpty()) {
                    coinManager.spendCoins(5)
                    getSharedPreferences(PREFS_PROFILE, MODE_PRIVATE)
                        .edit().putString("nickname", newName).apply()
                    nicknameText.text = newName
                }
            }
            .setNegativeButton(getString(R.string.nickname_decline), null)
            .show()
    }

    // ─── Photo ───────────────────────────────────────────────────────────────

    private fun loadPhoto(uri: Uri) {
        try {
            profilePhoto.setImageURI(uri)
        } catch (e: SecurityException) {
            // Дозвіл втрачено — прибираємо збережений URI
            getSharedPreferences(PREFS_PROFILE, MODE_PRIVATE)
                .edit().remove("photo_uri").apply()
            profilePhoto.setImageResource(R.drawable.ic_person)
        }
    }

    private fun openGallery() {
        // ACTION_OPEN_DOCUMENT дає persistable URI на відміну від ACTION_PICK
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        }
        pickPhoto.launch(intent)
    }

    // ─── HDH helpers ─────────────────────────────────────────────────────────

    private fun hdhCountThisMonth(): Int {
        val prefs  = getSharedPreferences("hdh_calendar", MODE_PRIVATE)
        val prefix = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"))
        return prefs.all.keys.count { it.startsWith(prefix) && prefs.getBoolean(it, false) }
    }

    private fun daysLeftInMonth(): Int {
        val now       = LocalDate.now()
        val lastDay   = now.withDayOfMonth(now.lengthOfMonth())
        return now.until(lastDay).days + 1
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
        finish()
    }
}