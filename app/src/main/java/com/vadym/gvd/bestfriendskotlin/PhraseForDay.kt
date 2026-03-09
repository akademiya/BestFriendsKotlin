package com.vadym.gvd.bestfriendskotlin

import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar
import com.vadym.gvd.bestfriendskotlin.shimjeong_shop.CoinManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PhraseForDay : MainActivity() {

    // ── Константи ────────────────────────────────────────────────────────────
    companion object {
        private const val PREFS_NAME         = "PhraseForDay"
        private const val KEY_PHRASE_TEXT    = "phraseText"
        private const val KEY_SCROLL_CLOSED  = "svitokClose"
        private const val KEY_LAST_OPEN_DATE = "lastOpenDate"
        private const val KEY_HISTORY        = "phraseHistory"

        private const val PHRASE_PREFIX      = "f"
        private const val PHRASE_COUNT       = 318
        private const val NO_REPEAT_WINDOW   = 100  // не повторювати останні N фраз
    }

    // ── Поля ─────────────────────────────────────────────────────────────────
    private lateinit var phraseTextView: TextView
    private lateinit var textOnButton: TextView
    private lateinit var coinManager: CoinManager

    // Генеруємо список програмно — не треба вручну писати 318 рядків
    private val phrases: List<String> by lazy {
        (1..PHRASE_COUNT).map { "$PHRASE_PREFIX$it" }
    }

    // ── Lifecycle ─────────────────────────────────────────────────────────────
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_phrase_for_day)

        phraseTextView = findViewById(R.id.text_phrase)
        textOnButton   = findViewById(R.id.text_on_button)
        coinManager    = CoinManager(this)

        val scrollClosed: ImageView = findViewById(R.id.scrollClosed)
        val scrollOpened: ImageView = findViewById(R.id.scrollOpened)

        setupToolbar()
        setupBackPress()
        restoreSavedPhrase()

        if (isBoxOpenableToday()) {
            val prefs      = getPrefs()
            val wasClosed  = prefs.getBoolean(KEY_SCROLL_CLOSED, false)

            if (wasClosed) {
                phraseTextView.visibility = View.GONE
                textOnButton.visibility   = View.VISIBLE
                scrollClosed.visibility   = View.VISIBLE
                scrollOpened.visibility   = View.GONE
            }

            scrollClosed.setOnClickListener {
                animateScrollOpen(scrollClosed, scrollOpened)
                textOnButton.visibility = View.GONE
                showNextPhrase()
                saveCurrentDateAsLastOpenDate()
                awardDailyCoins()
            }
        } else {
            textOnButton.visibility = View.GONE
            scrollClosed.visibility = View.GONE
            scrollOpened.visibility = View.VISIBLE
        }
    }

    // ── UI setup ──────────────────────────────────────────────────────────────
    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { navigateBack() }
    }

    /** Замінює deprecated onBackPressed() */
    private fun setupBackPress() {
        onBackPressedDispatcher.addCallback(this) { navigateBack() }
    }

    // ── Логіка фраз ───────────────────────────────────────────────────────────

    /**
     * Повертає рандомну фразу, яка не зустрічалась серед останніх [NO_REPEAT_WINDOW].
     * Якщо всі фрази вже у вікні (теоретично неможливо при 318 > 100) — скидає історію.
     */
    private fun getNextUniquePhrase(): String {
        val history    = loadHistory().toMutableList()
        val candidates = phrases.filterNot { it in history }

        // Якщо кандидатів не залишилось (мала кількість фраз) — скидаємо вікно
        val available  = candidates.ifEmpty {
            history.clear()
            phrases
        }

        val chosen = available.random()

        // Оновлюємо вікно: додаємо новий, прибираємо старий якщо перевищили розмір
        history.add(chosen)
        if (history.size > NO_REPEAT_WINDOW) {
            history.removeAt(0)
        }
        saveHistory(history)

        return chosen
    }

    private fun showNextPhrase() {
        val phraseKey = getNextUniquePhrase()
        val resId     = resources.getIdentifier(phraseKey, "string", packageName)

        phraseTextView.visibility = View.VISIBLE
        phraseTextView.text       = if (resId != 0) getString(resId) else phraseKey

        getPrefs().edit()
            .putString(KEY_PHRASE_TEXT, phraseKey)
            .putBoolean(KEY_SCROLL_CLOSED, true)
            .apply()
    }

    private fun restoreSavedPhrase() {
        val savedKey = getPrefs().getString(KEY_PHRASE_TEXT, null) ?: return
        val resId    = resources.getIdentifier(savedKey, "string", packageName)
        phraseTextView.text = if (resId != 0) getString(resId) else ""
    }

    // ── Збереження/читання ────────────────────────────────────────────────────
    private fun getPrefs() = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    /** Зберігаємо історію як рядок "f1,f5,f23,..." */
    private fun saveHistory(history: List<String>) {
        getPrefs().edit().putString(KEY_HISTORY, history.joinToString(",")).apply()
    }

    private fun loadHistory(): List<String> {
        val raw = getPrefs().getString(KEY_HISTORY, null) ?: return emptyList()
        return raw.split(",").filter { it.isNotBlank() }
    }

    private fun isBoxOpenableToday(): Boolean {
        val lastOpenDate = getPrefs().getString(KEY_LAST_OPEN_DATE, null)
        return lastOpenDate != getCurrentDate()
    }

    private fun saveCurrentDateAsLastOpenDate() {
        getPrefs().edit().putString(KEY_LAST_OPEN_DATE, getCurrentDate()).apply()
    }

    private fun getCurrentDate(): String =
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    // ── Монети ────────────────────────────────────────────────────────────────
    private fun awardDailyCoins() {
        coinManager.addCoins(CoinManager.COINS_PER_DAY)

        // Streak — перевіряємо серію і нараховуємо бонус якщо треба
        val streakBonus = coinManager.recordDailyOpen(getCurrentDate())
        val streak      = coinManager.currentStreak

        val root = findViewById<View>(android.R.id.content)

        if (streakBonus) {
            // 🎉 Бонус за 7 днів підряд
            Snackbar.make(root, "+${CoinManager.COINS_PER_DAY} 심정 Coin", Snackbar.LENGTH_SHORT)
                .show()

            // Показуємо окремий діалог про бонус
            android.os.Handler(mainLooper).postDelayed({
                com.google.android.material.dialog.MaterialAlertDialogBuilder(this)
                    .setTitle("🔥 ${streak} " + getString(R.string.bonus_7days))
                    .setMessage(getString(R.string.bonus_congratulation, CoinManager.COINS_STREAK_BONUS, CoinManager.STREAK_DAYS))
                    .setPositiveButton(android.R.string.ok, null)
                    .show()
            }, 600) // затримка щоб Snackbar встиг показатись
        } else {
            // Звичайне нарахування + показуємо поточну серію
            val streakText = if (streak > 1) "  🔥 $streak" else ""
            Snackbar.make(root, "+${CoinManager.COINS_PER_DAY} 심정 Coin$streakText", Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    // ── Анімація ──────────────────────────────────────────────────────────────
    private fun animateScrollOpen(scrollClosed: ImageView, scrollOpened: ImageView) {
        scrollClosed.animate()
            .scaleX(0f).scaleY(0f).alpha(0f)
            .setDuration(500)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator) {
                    scrollClosed.visibility = View.GONE
                    scrollOpened.apply {
                        visibility = View.VISIBLE
                        alpha  = 0f
                        scaleX = 0f
                        scaleY = 0f
                        animate().scaleX(1f).scaleY(1f).alpha(1f).setDuration(500).start()
                    }
                }
                override fun onAnimationEnd(p0: Animator)    {}
                override fun onAnimationCancel(p0: Animator) {}
                override fun onAnimationRepeat(p0: Animator) {}
            })
            .start()
    }

    // ── Навігація ─────────────────────────────────────────────────────────────
    private fun navigateBack() {
        startActivity(
            Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                putExtra(MainActivity.EXTRA_OPEN_DRAWER, true)
            }
        )
        finish()
    }
}