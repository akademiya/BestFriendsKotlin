package com.vadym.gvd.bestfriendskotlin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.vadym.gvd.bestfriendskotlin.calendar.HeavenlyCalendarView
import com.vadym.gvd.bestfriendskotlin.condition.ConditionView
import com.vadym.gvd.bestfriendskotlin.father_kido.FatherKidoView
import com.vadym.gvd.bestfriendskotlin.holy_days.HolyDaysView
import com.vadym.gvd.bestfriendskotlin.kido.PersonView
import com.vadym.gvd.bestfriendskotlin.shimjeong_shop.CardShopActivity
import com.vadym.gvd.bestfriendskotlin.traditions.TraditionsView
import java.net.URL
import java.util.Locale

open class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var notificationIcon: ImageView
    private lateinit var prefs: SharedPreferences
    private val storage = FirebaseStorage()

    // ─── Lifecycle ────────────────────────────────────────────────────────────

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocale()
        setContentView(R.layout.activity_main)
        CheckTheme.checkTheme(this, delegate)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        drawer = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)

        setupToolbar()
        setupNavMenu()

        // Відкрити drawer одразу, якщо повернулись з іншої activity
        if (savedInstanceState == null && intent.getBooleanExtra(EXTRA_OPEN_DRAWER, false)) {
            drawer.post { drawer.openDrawer(GravityCompat.START) }
        }

        if (isNetworkAvailable()) {
            GetVersionCode(this).execute()
        }



        prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        notificationIcon = findViewById(R.id.notification_from_admin)

        notificationIcon.setOnClickListener {
            // Ховаємо іконку одразу при кліку
            notificationIcon.visibility = View.GONE
            startActivity(Intent(this, InfoView::class.java))
        }

        checkInfoMessage()
    }

//    private val drawerLauncher = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ) { result ->
//        if (result.resultCode == RESULT_OK) {
//            drawer.post { drawer.openDrawer(GravityCompat.START) }
//        }
//    }

//    override fun onNewIntent(intent: Intent) {
//        super.onNewIntent(intent)
//        setIntent(intent)
//        if (intent.getBooleanExtra(EXTRA_OPEN_DRAWER, false)) {
//            shouldOpenDrawer = true
//        }
//    }

    override fun onResume() {
        super.onResume()
        checkInfoMessage()
        // Спрацює і коли повертаємось через finish() з дочірнього Activity
//        if (intent.getBooleanExtra(EXTRA_OPEN_DRAWER, false) || shouldOpenDrawer) {
//            shouldOpenDrawer = false
//            intent.removeExtra(EXTRA_OPEN_DRAWER) // щоб не відкривався повторно
//            drawer.post { drawer.openDrawer(GravityCompat.START) }
//        }
    }

    override fun attachBaseContext(newBase: Context) {
        val lang = newBase.savedLanguage
        super.attachBaseContext(newBase.withLocale(lang))
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    // ─── Setup ────────────────────────────────────────────────────────────────

    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        ActionBarDrawerToggle(
            this, drawer, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ).also { toggle ->
            drawer.addDrawerListener(toggle)
            toggle.syncState()
        }
    }

    private fun setupNavMenu() {
        val ukraineOnly = isUserFromUkraine()
        listOf(R.id.nav_ua_sj, R.id.nav_birthday).forEach { id ->
            navigationView.menu.findItem(id)?.isVisible = ukraineOnly
        }
        navigationView.setNavigationItemSelectedListener(this)
    }

    // ─── Navigation ───────────────────────────────────────────────────────────

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        handleNavigation(item.itemId)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.getBooleanExtra(EXTRA_OPEN_DRAWER, false)) {
            drawer.post { drawer.openDrawer(GravityCompat.START) }
        }
    }

    private fun handleNavigation(itemId: Int) {
        val activityMap = mapOf(
            R.id.nav_mense           to MenseView::class.java,
            R.id.nav_anthem          to AnthemView::class.java,
            R.id.nav_kido            to PersonView::class.java,
            R.id.nav_father_kido     to FatherKidoView::class.java,
            R.id.nav_phrase_day      to PhraseForDay::class.java,
            R.id.nav_kido_explanation to ExplanationView::class.java,
            R.id.nav_holly_days      to HolyDaysView::class.java,
            R.id.nav_kido_condition  to ConditionView::class.java,
            R.id.nav_traditions      to TraditionsView::class.java,
            R.id.nav_calendar        to HeavenlyCalendarView::class.java,
            R.id.nav_info            to InfoView::class.java,
            R.id.nav_exercise        to ExerciseView::class.java,
            R.id.nav_shop            to CardShopActivity::class.java,
            R.id.nav_hdh             to HDHView::class.java
        )

        activityMap[itemId]?.let { cls ->
            startActivity(
                Intent(this, cls)
                    .noAnimation()
                    .putExtra(EXTRA_OPEN_DRAWER, true) // передаємо сигнал
            )
            return
        }

        when (itemId) {
            R.id.nav_ua_sj    -> openApp("com.mattermost.rn", "https://umua.org/hpwords/channels/town-square")
            R.id.nav_birthday -> openApp(
                "com.vadym.birthday",
                "https://sites.google.com/view/birthday-app/main"
            )
            R.id.nav_facebook -> startActivity(openFacebookIntent(this))
            R.id.nav_share    -> shareApp()
            R.id.nav_send     -> sendEmail()
        }
    }

    // ─── External apps ────────────────────────────────────────────────────────

    private fun openApp(packageName: String, fallbackUrl: String) {
        val intent = packageManager.getLaunchIntentForPackage(packageName)
            ?: Intent(Intent.ACTION_VIEW, Uri.parse(fallbackUrl))
        startActivity(intent)
    }

    open fun openFacebookIntent(context: Context): Intent {
        val url = "https://www.facebook.com/groups/tfprayer"
        return try {
            context.packageManager.getPackageInfo("com.facebook.katana", 0)
            Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=$url"))
        } catch (e: Exception) {
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
        }
    }

    private fun shareApp() {
        val shareBody = getString(R.string.share_body)
        val appUrl = URL("https", "play.google.com", "store/apps/details?id=me.vadym.adv.tfprayer")
        startActivity(
            Intent.createChooser(
                Intent(Intent.ACTION_SEND).apply {
                    noAnimation()
                    type = "text/plain"
                    putExtra(Intent.EXTRA_SUBJECT, "True Life")
                    putExtra(Intent.EXTRA_TEXT, "$shareBody$appUrl")
                },
                getString(R.string.share_by)
            )
        )
    }

    private fun sendEmail() {
        startActivity(
            Intent.createChooser(
                Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:gms.nppn@gmail.com")).noAnimation(),
                "True Life"
            )
        )
    }

    // ─── Locale ───────────────────────────────────────────────────────────────

    private fun loadLocale() = setLocale(this, savedLanguage)

    fun setLocale(context: Context, languageCode: String): Context =
        context.withLocale(languageCode).also {
            context.getSharedPreferences("AppSettings", MODE_PRIVATE)
                .edit().putString("language", languageCode).apply()
        }


    // ─── Info message from admin ───────────────────────────────────────────────────────────────
    private fun checkInfoMessage() {
        storage.infoMessageFromFB { message ->
            if (message.isNullOrEmpty()) {
                notificationIcon.visibility = View.GONE
                return@infoMessageFromFB
            }

            val lastSeenMessage = prefs.getString("last_seen_info_message", null)
            // Показуємо іконку тільки якщо повідомлення нове (або ще не переглянуте)
            notificationIcon.visibility =
                if (message != lastSeenMessage) View.VISIBLE else View.GONE
        }
    }

    // ─── Helpers ──────────────────────────────────────────────────────────────

    private fun isUserFromUkraine() =
        Locale.getDefault().country.equals("ua", ignoreCase = true)

    fun isNetworkAvailable(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val caps = cm.getNetworkCapabilities(cm.activeNetwork) ?: return false
        return caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                || caps.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }

    // ─── Extensions (можна винести в окремий файл Extensions.kt) ──────────────

    private val Context.savedLanguage: String
        get() = getSharedPreferences("AppSettings", MODE_PRIVATE)
            .getString("language", "en") ?: "en"

    private fun Context.withLocale(languageCode: String): Context {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = resources.configuration.also { it.setLocale(locale) }
        @Suppress("DEPRECATION")
        resources.updateConfiguration(config, resources.displayMetrics)
        return createConfigurationContext(config)
    }

    companion object {
        const val EXTRA_OPEN_DRAWER = "extra_open_drawer"
    }
}