package com.vadym.gvd.bestfriendskotlin

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.google.android.gms.analytics.HitBuilders
import com.google.android.material.navigation.NavigationView
import com.vadym.gvd.bestfriendskotlin.condition.ConditionView
import com.vadym.gvd.bestfriendskotlin.father_kido.FatherKidoView
import com.vadym.gvd.bestfriendskotlin.kido.PersonView
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL

open class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CheckTheme.checkTheme(this, delegate)

        analyticsTracker()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        GetVersionCode(this).execute()
    }

    private fun analyticsTracker() {
        tracker().setScreenName("MainActivity")
        tracker().send(HitBuilders.ScreenViewBuilder().build())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.nav_share) { return true }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_mense -> startActivity(Intent(this, MenseView::class.java).noAnimation())
            R.id.nav_anthem -> startActivity(Intent(this, AnthemView::class.java).noAnimation())
            R.id.nav_kido -> startActivity(Intent(this, PersonView::class.java).noAnimation())
            R.id.nav_father_kido -> startActivity(Intent(this, FatherKidoView::class.java).noAnimation())
            R.id.nav_kido_explanation -> startActivity(Intent(this, ExplanationView::class.java).noAnimation())
            R.id.nav_kido_condition -> startActivity(Intent(this, ConditionView::class.java).noAnimation())
            R.id.nav_experiences_prayer -> startActivity(Intent(this, ExperiencesPrayerView::class.java).noAnimation())
            R.id.nav_info -> startActivity(Intent(this, InfoView::class.java))
            R.id.nav_facebook -> startActivity(openFacebookIntent(this))
            R.id.nav_share -> {
                val sharingIntent = Intent(Intent.ACTION_SEND)
                val shareBody = getString(R.string.share_body)
                sharingIntent.apply {
                    noAnimation()
                    type = "text/plain"
                    putExtra(Intent.EXTRA_SUBJECT, "True Father Prayers")
                    putExtra(Intent.EXTRA_TEXT, shareBody + URL("https", "play.google.com", "store/apps/details?id=me.vadym.adv.tfprayer"))
                }
                startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_by)))
            }

            R.id.nav_send -> {
                val uri = Uri.parse("mailto:vadym.adv@gmail.com")
                val sendIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
                sendIntent.data = uri
                sendIntent.noAnimation()
                startActivity(Intent.createChooser(sendIntent, "True Father Prayers"))
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    open fun openFacebookIntent(context: Context): Intent? {
        val url = "https://www.facebook.com/groups/tfprayer"
        return try {
            context.packageManager.getPackageInfo("com.facebook.katana", 0)
            Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=$url"))
        } catch (e: Exception) {
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}