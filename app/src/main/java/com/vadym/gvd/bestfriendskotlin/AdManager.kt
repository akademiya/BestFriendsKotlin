package com.vadym.gvd.bestfriendskotlin

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

/**
 * Єдина точка керування всією рекламою: банер + interstitial + підписка "без реклами".
 *
 * Lifecycle:
 *   1. [init] — викликати ОДИН РАЗ з LoadingView після згоди GDPR.
 *   2. [setupBanner] — замість Admob.initializeAdmob() у будь-якому Activity/Fragment.
 *   3. [tryShowOnAppStart] — при запуску з LoadingView.
 *   4. [tryShowAfterScrollOpen] — після відкриття сувою з фразою.
 *   5. [hideForMonth] / [hideForever] — покупка "без реклами" у InfoView.
 */
object AdManager {

    // ── ID ───────────────────────────────────────────────────────────────────
    private const val INTERSTITIAL_ID = "ca-app-pub-5169531562006723/3853594723"

    // ── SharedPreferences ────────────────────────────────────────────────────
    private const val PREFS_AD          = "AdManagerPrefs"
    private const val KEY_LAST_START_AD = "lastAppStartAd"   // Long  — timestamp мс
    private const val KEY_LAST_SCROLL_AD= "lastScrollAd"     // String — "yyyy-MM-dd"
    private const val KEY_AD_HIDE_UNTIL = "ad_hide_until"    // Long  — timestamp мс; -1 = назавжди

    // ── Вартість підписки (SC) ────────────────────────────────────────────────
    const val COST_HIDE_MONTH   = 90
    const val COST_HIDE_FOREVER = 210

    // ── Інтервал interstitial при старті ─────────────────────────────────────
    private val START_INTERVAL_MS = TimeUnit.HOURS.toMillis(24)

    // ── Внутрішній стан ───────────────────────────────────────────────────────
    private var interstitialAd: InterstitialAd? = null
    private var isLoading   = false
    private var initialized = false

    // =========================================================================
    // Ініціалізація SDK
    // =========================================================================

    /**
     * Викликати ОДИН РАЗ після GDPR-згоди.
     * Якщо реклама прихована підпискою — SDK взагалі не ініціалізується.
     */
    fun init(context: Context) {
        if (initialized) return
        initialized = true
        if (isAdHidden(context)) return          // не витрачаємо ресурси
        MobileAds.initialize(context) { loadInterstitial(context) }
    }

    // =========================================================================
    // Банерна реклама
    // =========================================================================

    /**
     * Замінює прямий виклик Admob.initializeAdmob().
     * Перевіряє підписку і мережу — якщо одне з них блокує показ,
     * AdView залишається GONE і запит до мережі не робиться.
     */
    fun setupBanner(activity: MainActivity, adView: AdView, vararg dividers: android.view.View) {
        if (isAdHidden(activity) || !activity.isNetworkAvailable()) {
            adView.visibility = android.view.View.GONE
            dividers.forEach { it.visibility = android.view.View.GONE }
            return
        }
        activity.window.decorView.post {
            adView.visibility = android.view.View.VISIBLE
            dividers.forEach { it.visibility = android.view.View.VISIBLE }
            adView.loadAd(AdRequest.Builder().build())
        }
    }

    // =========================================================================
    // Interstitial реклама
    // =========================================================================

    /** При запуску застосунку — раз на 24 год. */
    fun tryShowOnAppStart(activity: Activity, onFinished: () -> Unit) {
        if (isAdHidden(activity)) { onFinished(); return }
        val prefs   = prefs(activity)
        val elapsed = System.currentTimeMillis() - prefs.getLong(KEY_LAST_START_AD, 0L)
        if (elapsed >= START_INTERVAL_MS) {
            showInterstitial(
                activity,
                onShown    = { prefs.edit().putLong(KEY_LAST_START_AD, System.currentTimeMillis()).apply() },
                onFinished = onFinished
            )
        } else {
            onFinished()
        }
    }

    /** Після відкриття сувою з фразою — раз на добу. */
    fun tryShowAfterScrollOpen(activity: Activity, onFinished: () -> Unit = {}) {
        if (isAdHidden(activity)) { onFinished(); return }
        val prefs = prefs(activity)
        val today = today()
        if (prefs.getString(KEY_LAST_SCROLL_AD, null) != today) {
            showInterstitial(
                activity,
                onShown    = { prefs.edit().putString(KEY_LAST_SCROLL_AD, today).apply() },
                onFinished = onFinished
            )
        } else {
            onFinished()
        }
    }

    // =========================================================================
    // Підписка "без реклами"
    // =========================================================================

    /** true → рекламу не показувати і не ініціалізувати */
    fun isAdHidden(context: Context): Boolean {
        val until = prefs(context).getLong(KEY_AD_HIDE_UNTIL, 0L)
        return until == -1L || until > System.currentTimeMillis()
    }

    /** Повертає true якщо монети списано і налаштування збережено. */
    fun hideForMonth(context: Context, coinManager: com.vadym.gvd.bestfriendskotlin.shimjeong_shop.CoinManager): Boolean {
        if (!coinManager.spendCoins(COST_HIDE_MONTH)) return false
        val until = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(30)
        prefs(context).edit().putLong(KEY_AD_HIDE_UNTIL, until).apply()
        return true
    }

    fun hideForever(context: Context, coinManager: com.vadym.gvd.bestfriendskotlin.shimjeong_shop.CoinManager): Boolean {
        if (!coinManager.spendCoins(COST_HIDE_FOREVER)) return false
        prefs(context).edit().putLong(KEY_AD_HIDE_UNTIL, -1L).apply()
        return true
    }

    /** Скільки мілісекунд залишилось (0L якщо назавжди або неактивно). */
    fun remainingMs(context: Context): Long {
        val until = prefs(context).getLong(KEY_AD_HIDE_UNTIL, 0L)
        return if (until > 0L) maxOf(0L, until - System.currentTimeMillis()) else 0L
    }

    // =========================================================================
    // Внутрішні помічники
    // =========================================================================

    private fun showInterstitial(activity: Activity, onShown: () -> Unit, onFinished: () -> Unit) {
        val ad = interstitialAd
        if (ad == null) {
            loadInterstitial(activity)
            onFinished()
            return
        }
        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdShowedFullScreenContent() {
                onShown()
                interstitialAd = null
            }
            override fun onAdDismissedFullScreenContent() {
                loadInterstitial(activity)
                onFinished()
            }
            override fun onAdFailedToShowFullScreenContent(e: AdError) {
                interstitialAd = null
                loadInterstitial(activity)
                onFinished()
            }
        }
        ad.show(activity)
    }

    private fun loadInterstitial(context: Context) {
        if (isLoading || interstitialAd != null) return
        isLoading = true
        InterstitialAd.load(
            context.applicationContext,
            INTERSTITIAL_ID,
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

    private fun prefs(context: Context) =
        context.getSharedPreferences(PREFS_AD, Context.MODE_PRIVATE)

    private fun today(): String =
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
}