package com.vadym.gvd.bestfriendskotlin

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Єдина точка керування Interstitial рекламою для всього застосунку.
 *
 * Два сценарії показу:
 *  1. [tryShowOnAppStart]      — при запуску, раз на добу (викликати з LoadingView)
 *  2. [tryShowAfterScrollOpen] — після відкриття сувою з фразою (раз на добу)
 */
object AdManager {

    // ── Замінити на реальні ID перед релізом! ────────────────────────────────
    private const val AD_UNIT_ID = "ca-app-pub-5169531562006723/3853594723" // test ID

    // ── SharedPreferences ────────────────────────────────────────────────────
    private const val PREFS_NAME            = "AdManagerPrefs"
    private const val KEY_LAST_APP_START_AD = "lastAppStartAd" // Long — timestamp
    private const val KEY_LAST_SCROLL_AD    = "lastScrollAd"   // String — "yyyy-MM-dd"

    // ── Інтервали ────────────────────────────────────────────────────────────
    private const val APP_START_INTERVAL_MS = 24 * 60 * 60 * 1000L // 24 год

    // ── Стан ─────────────────────────────────────────────────────────────────
    private var interstitialAd: InterstitialAd? = null
    private var isLoading   = false
    private var initialized = false

    // ── Ініціалізація ─────────────────────────────────────────────────────────

    /**
     * Викликати ОДИН РАЗ з LoadingView після отримання згоди GDPR.
     * Ініціалізує AdMob SDK і одразу починає завантаження реклами.
     */
    fun init(context: Context) {
        if (initialized) return
        initialized = true
        MobileAds.initialize(context) { load(context) }
    }

    // ── Публічне API ──────────────────────────────────────────────────────────

    /**
     * Показати рекламу при запуску — раз на 24 год.
     * Викликати з LoadingView після init().
     * [onFinished] — обов'язково викликається завжди (показали чи ні),
     *               використовуй для переходу в MainActivity.
     */
    fun tryShowOnAppStart(activity: Activity, onFinished: () -> Unit) {
        val prefs   = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val elapsed = System.currentTimeMillis() - prefs.getLong(KEY_LAST_APP_START_AD, 0L)

        if (elapsed >= APP_START_INTERVAL_MS) {
            showAd(
                activity,
                onShown    = { prefs.edit().putLong(KEY_LAST_APP_START_AD, System.currentTimeMillis()).apply() },
                onFinished = onFinished
            )
        } else {
            onFinished()
        }
    }

    /**
     * Показати рекламу після відкриття сувою — раз на добу.
     * Викликати з PhraseForDay після анімації.
     * [onFinished] — опціональний, застосунок залишається на екрані фрази.
     */
    fun tryShowAfterScrollOpen(activity: Activity, onFinished: () -> Unit = {}) {
        val prefs = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val today = currentDate()

        if (prefs.getString(KEY_LAST_SCROLL_AD, null) != today) {
            showAd(
                activity,
                onShown    = { prefs.edit().putString(KEY_LAST_SCROLL_AD, today).apply() },
                onFinished = onFinished
            )
        } else {
            onFinished()
        }
    }

    // ── Внутрішня логіка ──────────────────────────────────────────────────────

    private fun showAd(activity: Activity, onShown: () -> Unit, onFinished: () -> Unit) {
        val ad = interstitialAd
        if (ad == null) {
            load(activity)
            onFinished() // реклама не готова — пропускаємо без затримки
            return
        }

        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdShowedFullScreenContent() {
                onShown()
                interstitialAd = null
            }
            override fun onAdDismissedFullScreenContent() {
                load(activity)   // готуємо наступну заздалегідь
                onFinished()
            }
            override fun onAdFailedToShowFullScreenContent(e: AdError) {
                interstitialAd = null
                load(activity)
                onFinished()
            }
        }

        ad.show(activity)
    }

    private fun load(context: Context) {
        if (isLoading || interstitialAd != null) return
        isLoading = true

        InterstitialAd.load(
            context.applicationContext,
            AD_UNIT_ID,
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    isLoading      = false
                }
                override fun onAdFailedToLoad(e: LoadAdError) {
                    interstitialAd = null
                    isLoading      = false
                }
            }
        )
    }

    private fun currentDate(): String =
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
}