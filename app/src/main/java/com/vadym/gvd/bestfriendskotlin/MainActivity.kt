package com.vadym.gvd.bestfriendskotlin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
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
import com.vadym.gvd.bestfriendskotlin.treelife.TreeOfLifeView
import com.vadym.gvd.bestfriendskotlin.treelife.TreeProfileView
import java.net.URL
import java.time.LocalDate
import java.util.Locale

open class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var notificationIcon: ImageView
    private lateinit var prefs: SharedPreferences
    private val storage = FirebaseStorage()
    private val updateChecker by lazy { AppUpdateChecker(this) }

    // ─── Lifecycle ────────────────────────────────────────────────────────────

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocale()
        setContentView(R.layout.activity_main)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        drawer = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)

        setupToolbar()
        setupNavMenu()
        updateChecker.checkOnLaunchIfNeeded()

        // Відкрити drawer одразу, якщо повернулись з іншої activity
        if (savedInstanceState == null && intent.getBooleanExtra(EXTRA_OPEN_DRAWER, false)) {
            drawer.post { drawer.openDrawer(GravityCompat.START) }
        }

        if (isNetworkAvailable()) {
            GetVersionCode(this).check()
        }



        prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        notificationIcon = findViewById(R.id.notification_from_admin)

        notificationIcon.setOnClickListener {
            val currentMessage = prefs.getString("last_firebase_info_message", null)
            if (currentMessage != null) {
                prefs.edit().putString("last_seen_info_message", currentMessage).apply()
            }
            notificationIcon.visibility = View.GONE
            startActivity(Intent(this, InfoView::class.java))
        }

        val profilePrefs = getSharedPreferences("tree_profile", MODE_PRIVATE)

        if (!profilePrefs.contains("join_date")) {
            profilePrefs.edit().putString("join_date", LocalDate.now().toString()).apply()
        }

        checkInfoMessage()

        val header = navigationView.getHeaderView(0)
        val maxHeightPx = (resources.displayMetrics.heightPixels * 0.28).toInt()
        header.layoutParams.height = maxHeightPx
        header.requestLayout()
    }


    override fun onResume() {
        super.onResume()
        checkInfoMessage()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase.withLocale(newBase.savedLanguage))
    }

    private fun loadLocale() = setLocale(savedLanguage)

    fun setLocale(context: Context, languageCode: String): Context =
        context.withLocale(languageCode).also {
            context.getSharedPreferences("AppSettings", MODE_PRIVATE)
                .edit().putString("language", languageCode).apply()
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
            R.id.nav_holly_days      to HolyDaysView::class.java,
            R.id.nav_kido_condition  to ConditionView::class.java,
            R.id.nav_traditions      to TraditionsView::class.java,
            R.id.nav_calendar        to HeavenlyCalendarView::class.java,
            R.id.nav_info            to InfoView::class.java,
            R.id.nav_exercise        to ExerciseView::class.java,
            R.id.nav_shop            to CardShopActivity::class.java,
            R.id.nav_treelife        to TreeOfLifeView::class.java,
//            R.id.nav_profile         to TreeProfileView::class.java,
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
            R.id.nav_8books   -> openSite(getString(R.string.site_8books_link))
            R.id.nav_birthday -> Toast.makeText(this, "Coming soon. I'm fixing the code", Toast.LENGTH_SHORT).show()
//                openApp( "com.vadym.birthday", "https://sites.google.com/view/birthday-app/main" )
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

    private fun openSite(url: String) {
        val uri = Uri.parse(url)
        Intent(Intent.ACTION_VIEW, uri).apply { noAnimation() }.also { startActivity(it) }
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



    // ─── Info message from admin ───────────────────────────────────────────────────────────────
    private fun checkInfoMessage() {
        storage.infoMessageFromFB { message ->
            if (message.isNullOrEmpty()) {
                notificationIcon.visibility = View.GONE
                return@infoMessageFromFB
            }

            prefs.edit().putString("last_firebase_info_message", message).apply()

            val lastSeenMessage = prefs.getString("last_seen_info_message", null)
            notificationIcon.visibility =
                if (message != lastSeenMessage) View.VISIBLE else View.GONE
        }
    }

    // ─── Helpers ──────────────────────────────────────────────────────────────

    private fun isUserFromUkraine() =
        Locale.getDefault().language.equals("uk", ignoreCase = true)


    companion object {
        const val EXTRA_OPEN_DRAWER = "extra_open_drawer"
    }
}