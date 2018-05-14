package com.example.user.bestfriendskotlin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.example.user.bestfriendskotlin.father_kido.FatherKidoView
import com.example.user.bestfriendskotlin.kido.PersonView
import kotlinx.android.synthetic.main.activity_main.*

open class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val toggle = ActionBarDrawerToggle(
                this,
                drawer_layout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.nav_share) { return true }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_main -> startActivity(Intent(this, MainActivity::class.java))

            R.id.nav_kido -> startActivity(Intent(this, PersonView::class.java))

            R.id.nav_father_kido -> startActivity(Intent(this, FatherKidoView::class.java))

            R.id.nav_share -> {
                val sharingIntent = Intent(android.content.Intent.ACTION_SEND)
                val shareBody = "Хочу поделиться данным приложением. Пробуй, пиши feedback. \nВот ссылка на APK: \n" +
                        "https://drive.google.com/open?id=1hFmdNiSxiD4kXU48CuJXi37_Y4Z8Ynil"
                sharingIntent.apply {
                    type = "text/plain"
                    putExtra(android.content.Intent.EXTRA_SUBJECT, "TF Pray")
                    putExtra(android.content.Intent.EXTRA_TEXT, shareBody)
                }
                startActivity(Intent.createChooser(sharingIntent, "Поделиться:"))
            }

            R.id.nav_send -> {
                val uri = Uri.parse("mailto:vadym.adv@gmail.com")
                val sendIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
                sendIntent.setData(uri)
                startActivity(Intent.createChooser(sendIntent, "TF Pray"))
            }

            R.id.nav_donation -> startActivity(Intent(this, DonationView::class.java))
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