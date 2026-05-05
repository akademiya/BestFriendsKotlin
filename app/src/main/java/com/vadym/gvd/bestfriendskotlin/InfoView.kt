package com.vadym.gvd.bestfriendskotlin

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.gms.ads.AdView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.snackbar.Snackbar
import com.vadym.gvd.bestfriendskotlin.easter_egg.CoinActivity
import com.vadym.gvd.bestfriendskotlin.shimjeong_shop.CoinManager
import java.util.concurrent.TimeUnit

class InfoView : CoinActivity() {

    private lateinit var privacyPolicy: TextView
    private lateinit var site: ShapeableImageView
    private lateinit var version: TextView

    private lateinit var cardAdMonth: View
    private lateinit var cardAdForever: View
    private lateinit var cardAdStatus: TextView

    private val updateChecker by lazy { AppUpdateChecker(this) }
    private val coinManager   by lazy { CoinManager(this) }
    private val rater by lazy {
        AppRater(
            context = this,
            coinManager = coinManager,
            onCoinsAwarded = { coins ->
                showSnack(getString(R.string.thanks_for_rate, "$coins"))
            }
        )
    }
    private val storage = FirebaseStorage()

    override val coinViewMap = mapOf(
        "coin_info_001" to R.id.coin_info_001,
        "coin_info_002" to R.id.coin_info_002,
        "coin_info_003" to R.id.coin_info_003
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_info)

        bindViews()
        setupToolbar()
        setupBackPress()
        setupListeners()
        loadData()
        refreshAdCards()
    }

    private fun bindViews() {
        privacyPolicy  = findViewById(R.id.private_policy)
        site           = findViewById(R.id.site)
        version        = findViewById(R.id.version)
        cardAdMonth    = findViewById(R.id.card_ad_month)
        cardAdForever  = findViewById(R.id.card_ad_forever)
        cardAdStatus   = findViewById(R.id.tv_ad_status)
    }

    private fun setupToolbar() {
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { navigateBack() }
    }

    private fun setupListeners() {
        privacyPolicy.movementMethod = LinkMovementMethod.getInstance()

        site.setOnClickListener {
            val uri = Uri.parse(resources.getString(R.string.site_link))
            Intent(Intent.ACTION_VIEW, uri).apply { noAnimation() }.also { startActivity(it) }
        }

        cardAdMonth.setOnClickListener { onBuyAdHide(forever = false) }
        cardAdForever.setOnClickListener { onBuyAdHide(forever = true) }
    }

    // -------------------------------------------------------------------------
    // Покупка приховування реклами
    // -------------------------------------------------------------------------

    private fun onBuyAdHide(forever: Boolean) {
        if (AdManager.isAdHidden(this)) {
            showSnack(getString(R.string.ad_already_hidden))
            return
        }

        val success = if (forever)
            AdManager.hideForever(this, coinManager)
        else
            AdManager.hideForMonth(this, coinManager)

        if (success) {
            val msg = if (forever) getString(R.string.ad_hidden_forever)
            else         getString(R.string.ad_hidden_month)
            showSnack(msg)
            refreshAdCards()
            hideAdBannerImmediately()
        } else {
            showSnack(getString(R.string.not_enough_coins))
        }
    }

    private fun hideAdBannerImmediately() {
        val adContainer = findViewById<AdView>(R.id.adView)
        adContainer.pause()
        adContainer.destroy()
        adContainer.visibility = View.GONE
    }

    // -------------------------------------------------------------------------
    // Оновлення стану карток залежно від поточного статусу
    // -------------------------------------------------------------------------

    private fun refreshAdCards() {
        val hidden = AdManager.isAdHidden(this)
        val remaining = AdManager.remainingMs(this)
        val isForever = hidden && remaining == 0L

        when {
            isForever -> {
                cardAdMonth.visibility   = View.GONE
                cardAdForever.visibility = View.GONE
                cardAdStatus.visibility  = View.VISIBLE
                cardAdStatus.text        = getString(R.string.ad_status_forever)
            }

            hidden -> {
                cardAdMonth.visibility   = View.VISIBLE
                cardAdForever.visibility = View.VISIBLE
                cardAdMonth.isEnabled    = false
                cardAdForever.isEnabled  = false
                cardAdMonth.alpha        = 0.4f
                cardAdForever.alpha      = 0.4f
                cardAdStatus.visibility  = View.VISIBLE
                cardAdStatus.text        = getString(
                    R.string.ad_status_days,
                    TimeUnit.MILLISECONDS.toDays(remaining)
                )
            }

            else -> {
                cardAdMonth.visibility   = View.VISIBLE
                cardAdForever.visibility = View.VISIBLE
                cardAdMonth.isEnabled    = true
                cardAdForever.isEnabled  = true
                cardAdMonth.alpha        = 1f
                cardAdForever.alpha      = 1f
                cardAdStatus.visibility  = View.GONE
            }
        }

//        if (hidden) {
//            cardAdMonth.isEnabled   = false
//            cardAdForever.isEnabled = false
//            cardAdMonth.alpha       = 0.4f
//            cardAdForever.alpha     = 0.4f
//
//            val remaining = AdManager.remainingMs(this)
//            cardAdStatus.visibility = View.VISIBLE
//            cardAdStatus.text = if (remaining == 0L) {
//                getString(R.string.ad_status_forever)
//            } else {
//                val days = TimeUnit.MILLISECONDS.toDays(remaining)
//                getString(R.string.ad_status_days, days)
//            }
//        } else {
//            cardAdMonth.isEnabled   = true
//            cardAdForever.isEnabled = true
//            cardAdMonth.alpha       = 1f
//            cardAdForever.alpha     = 1f
//            cardAdStatus.visibility = View.GONE
//        }
    }

    // -------------------------------------------------------------------------
    // Решта завантаження даних
    // -------------------------------------------------------------------------

    private fun loadData() {
        val adContainer  = findViewById<AdView>(R.id.adView)
        val infoMessage  = findViewById<TextView>(R.id.info_message)
        val prefs        = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        storage.infoMessageFromFB { message ->
            infoMessage.visibility = if (message.isNullOrEmpty()) View.GONE else View.VISIBLE
            infoMessage.text = message
            if (!message.isNullOrEmpty())
                prefs.edit().putString("last_seen_info_message", message).apply()
        }

        runCatching {
            "v. ${packageManager.getPackageInfo(packageName, 0).versionName}"
        }.onSuccess { version.text = it }

        version.setOnClickListener { updateChecker.checkManually() }

        AdManager.setupBanner(this, adContainer)

        rater.appLaunched()
    }

    private fun showSnack(msg: String) =
        Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()

    // -------------------------------------------------------------------------
    // Меню / мова / тема
    // -------------------------------------------------------------------------

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.language, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ko    -> setAppLanguage("ko")
            R.id.en    -> setAppLanguage("en")
            R.id.ua    -> setAppLanguage("uk")
            R.id.fr    -> setAppLanguage("fr")
            R.id.ru    -> setAppLanguage("ru")
            R.id.light -> setDarkMode(AppCompatDelegate.MODE_NIGHT_NO)
            R.id.dark  -> setDarkMode(AppCompatDelegate.MODE_NIGHT_YES)
            else       -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun setAppLanguage(code: String) {
        this.setLocale(code)
        Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }.also { startActivity(it) }
    }

    private fun setDarkMode(mode: Int) {
        DarkModePreferences(this).mode = mode
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    private fun setupBackPress() {
        onBackPressedDispatcher.addCallback(this) { navigateBack() }
    }

    private fun navigateBack() {
        Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra(EXTRA_OPEN_DRAWER, true)
        }.also { startActivity(it); finish() }
    }
}