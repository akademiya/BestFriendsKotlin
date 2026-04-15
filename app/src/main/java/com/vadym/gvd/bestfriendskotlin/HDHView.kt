package com.vadym.gvd.bestfriendskotlin

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.vadym.gvd.bestfriendskotlin.databinding.ViewHdhBinding
import com.vadym.gvd.bestfriendskotlin.easter_egg.CoinActivity
import com.vadym.gvd.bestfriendskotlin.shimjeong_shop.CoinManager
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

class HDHView : CoinActivity() {

    private lateinit var binding: ViewHdhBinding
    private var currentYearMonth = YearMonth.now()

    override val coinViewMap = mapOf(
        "coin_hdh_10" to R.id.coin_hdh_10,
        "coin_hdh_11" to R.id.coin_hdh_11
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ViewHdhBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupBackPress()
        setupCalendar()
        setupButtons()
        checkAndRewardPreviousMonth()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbar.setNavigationOnClickListener { navigateBack() }
    }

    private fun setupCalendar() {
        binding.calendarGrid.post { renderCalendar() }

        binding.btnPrevMonth.setOnClickListener {
            currentYearMonth = currentYearMonth.minusMonths(1)
            renderCalendar()
        }
        binding.btnNextMonth.setOnClickListener {
            currentYearMonth = currentYearMonth.plusMonths(1)
            renderCalendar()
        }
    }

    private fun getCellSize(): Int {
        val gridWidth = binding.calendarGrid.width
        Log.d("HDHView", "gridWidth=$gridWidth")
        return if (gridWidth > 0) gridWidth / 7 else 48.dp
    }

    private fun cellParams(cellSize: Int) = GridLayout.LayoutParams().apply {
        width = 0
        height = cellSize
        columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1f)
        rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1f)
        setMargins(2, 2, 2, 2)
    }

    private fun renderCalendar() {
        val cellSize = getCellSize()
        Log.d("HDHView", "cellSize=$cellSize, daysInMonth=${currentYearMonth.lengthOfMonth()}")
        val prefs = getSharedPreferences("hdh_calendar", Context.MODE_PRIVATE)
        val today = LocalDate.now()
        val now = LocalTime.now()
        val isMarkableNow = currentYearMonth == YearMonth.now()
                && now.hour >= 6 && now.hour < 8

        binding.tvMonthYear.text = currentYearMonth
            .format(DateTimeFormatter.ofPattern("LLLL yyyy", Locale.getDefault()))
            .replaceFirstChar { it.uppercase() }

        binding.calendarGrid.removeAllViews()

        // Заголовки днів тижня
        listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Нд").forEach { name ->
            binding.calendarGrid.addView(TextView(this).apply {
                text = name
                gravity = Gravity.CENTER
                setTextColor(ContextCompat.getColor(this@HDHView, R.color.colorPrimary))
                textSize = 12f
                typeface = android.graphics.Typeface.DEFAULT_BOLD
                layoutParams = cellParams(cellSize)
            })
        }

        // Порожні клітинки на початку
        val startOffset = currentYearMonth.atDay(1).dayOfWeek.value - 1
        repeat(startOffset) {
            binding.calendarGrid.addView(View(this).apply {
                layoutParams = cellParams(cellSize)
            })
        }

        // Дні місяця
        repeat(currentYearMonth.lengthOfMonth()) { i ->
            val day = i + 1
            val date = currentYearMonth.atDay(day)
            val dateKey = date.toString()
            val isMarked = prefs.getBoolean(dateKey, false)
            val isToday = date == today

            binding.calendarGrid.addView(TextView(this).apply {
                text = day.toString()
                gravity = Gravity.CENTER
                textSize = 14f

                val (bgRes, textColorRes) = when {
                    isToday && isMarked -> R.drawable.bg_day_marked to android.R.color.white
                    isToday             -> R.drawable.bg_day_today  to R.color.black_effective
                    isMarked            -> R.drawable.bg_day_marked to android.R.color.white
                    else                -> 0                        to R.color.black_effective
                }

                if (bgRes != 0) setBackgroundResource(bgRes)
                setTextColor(ContextCompat.getColor(this@HDHView, textColorRes))
                layoutParams = cellParams(cellSize)

                if (isToday && isMarkableNow && !isMarked) {
                    setOnClickListener {
                        prefs.edit().putBoolean(dateKey, true).apply()

                        val mediaPlayer = MediaPlayer.create(this@HDHView, R.raw.sj_coin)
                        mediaPlayer.setOnCompletionListener { it.release() }
                        mediaPlayer.start()

                        val coinManager = CoinManager(this@HDHView)
                        coinManager.addCoins(CoinManager.COINS_PER_HDH_DAY)
                        Snackbar.make(
                            binding.root,
                            getString(R.string.coins_per_hdh_day, CoinManager.COINS_PER_HDH_DAY),
                            Snackbar.LENGTH_LONG
                        ).show()

                        renderCalendar()
                    }
                }
            })
        }
    }

    private fun setupButtons() {
        binding.btnOpenHdh.setOnClickListener { openHDHApp() }
    }

    private fun countMarkedDays(yearMonth: YearMonth): Int {
        val prefs = getSharedPreferences("hdh_calendar", Context.MODE_PRIVATE)
        return (1..yearMonth.lengthOfMonth()).count { day ->
            prefs.getBoolean(yearMonth.atDay(day).toString(), false)
        }
    }

    private fun checkAndRewardPreviousMonth() {
        val previousMonth = YearMonth.now().minusMonths(1)
        val markedCount = countMarkedDays(previousMonth)
        if (markedCount >= 20) {
            val coinManager = CoinManager(this)
            val rewarded = coinManager.rewardForHDHMonth(previousMonth.toString())
            if (rewarded) {
                Toast.makeText(
                    this,
                    "🎉 +25 심정 for $markedCount HDH in ${previousMonth.month.getDisplayName(
                        java.time.format.TextStyle.FULL,
                        Locale.getDefault()
                    )}!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun openHDHApp() = openApp("com.vadym.hdhmeeting")

    private fun openApp(packageName: String) {
        val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
        if (launchIntent != null) {
            startActivity(launchIntent)
        } else {
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
            } catch (e: Exception) {
                startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$packageName&hl=uk")))
            }
        }
    }

    private val Int.dp get() = (this * resources.displayMetrics.density).toInt()

    private fun setupBackPress() {
        onBackPressedDispatcher.addCallback(this) { navigateBack() }
    }

    private fun navigateBack() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra(EXTRA_OPEN_DRAWER, true)
        }
        startActivity(intent)
        finish()
    }
}