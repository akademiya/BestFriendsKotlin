package com.vadym.gvd.bestfriendskotlin.kido

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.LinearLayout
import com.vadym.gvd.bestfriendskotlin.R


class FabView : AppCompatActivity() {

    lateinit var fab: FloatingActionButton
    lateinit var fab1: FloatingActionButton
    lateinit var fab2: FloatingActionButton

    lateinit var fabLayout1: LinearLayout
    lateinit var fabLayout2: LinearLayout

    lateinit var fabBGLayout: View
    var isFABOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fab)

        fabLayout1 = findViewById(R.id.fabLayout1)
        fabLayout2 = findViewById(R.id.fabLayout2)

        fab = findViewById(R.id.fab)
        fab1 = findViewById(R.id.fab1)
        fab2 = findViewById(R.id.fab2)

        fabBGLayout = findViewById(R.id.fabBGLayout)

        fab.setOnClickListener {
            if (!isFABOpen) showFABMenu() else closeFABMenu()
        }

        fabBGLayout.setOnClickListener { closeFABMenu() }

    }

    private fun showFABMenu() {
        isFABOpen = true
        fabLayout1.visibility = View.VISIBLE
        fabLayout2.visibility = View.VISIBLE
        fabBGLayout.visibility = View.VISIBLE

        fab.animate().rotationBy(180f)
        fabLayout1.animate().translationY(-resources.getDimension(R.dimen.first_fab))
        fabLayout2.animate().translationY(-resources.getDimension(R.dimen.second_fab))
    }

    private fun closeFABMenu() {
        isFABOpen = false
        fabBGLayout.visibility = View.GONE
        fab.animate().rotationBy(-180f)
        fabLayout1.animate().translationY(0f)
        fabLayout2.animate().translationY(0f)
    }

    override fun onBackPressed() {
        if (isFABOpen) closeFABMenu() else super.onBackPressed()
    }
}