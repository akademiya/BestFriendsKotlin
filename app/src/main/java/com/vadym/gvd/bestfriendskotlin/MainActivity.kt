package com.vadym.gvd.bestfriendskotlin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.google.android.gms.analytics.HitBuilders
import com.vadym.gvd.bestfriendskotlin.condition.Condition
import com.vadym.gvd.bestfriendskotlin.condition.ConditionView
import com.vadym.gvd.bestfriendskotlin.father_kido.FatherKidoView
import com.vadym.gvd.bestfriendskotlin.kido.PersonView
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL

open class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        analyticsTracker()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
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
            R.id.nav_mense -> startActivity(Intent(this, MenseView::class.java))
            R.id.nav_anthem -> startActivity(Intent(this, AnthemView::class.java))
            R.id.nav_kido -> startActivity(Intent(this, PersonView::class.java))
            R.id.nav_father_kido -> startActivity(Intent(this, FatherKidoView::class.java))
            R.id.nav_kido_explanation -> startActivity(Intent(this, ExplanationView::class.java))
            R.id.nav_kido_condition -> startActivity(Intent(this, ConditionView::class.java))
            R.id.nav_info -> startActivity(Intent(this, InfoView::class.java))
            R.id.nav_share -> {
                val sharingIntent = Intent(Intent.ACTION_SEND)
                val shareBody = getString(R.string.share_body)
                sharingIntent.apply {
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
                startActivity(Intent.createChooser(sendIntent, "True Father Prayers"))
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}