package com.vadym.gvd.bestfriendskotlin

import android.animation.Animator
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PhraseForDay : MainActivity() {

    private lateinit var phraseTextView: TextView
    private lateinit var textOnButton: TextView


    private val phrases: Array<String> by lazy {
        arrayOf(
            "f1", "f2", "f3", "f4", "f5", "f6", "f7", "f8", "f9", "f10",
            "f11", "f12", "f13", "f14", "f15", "f16", "f17", "f18", "f19", "f20",
            "f21", "f22", "f23", "f24", "f25", "f26", "f27", "f28", "f29", "f30",
            "f31", "f32", "f33", "f34", "f35", "f36", "f37", "f38", "f39", "f40",
            "f41", "f42", "f43", "f44", "f45", "f46", "f47", "f48", "f49", "f50",
            "f51", "f52", "f53", "f54", "f55", "f56", "f57", "f58", "f59", "f60",
            "f61", "f62", "f63", "f64", "f65", "f66", "f67", "f68", "f69", "f70",
            "f71", "f72", "f73", "f74", "f75", "f76", "f77", "f78", "f79", "f80",
            "f81", "f82", "f83", "f84", "f85", "f86", "f87", "f88", "f89", "f90",
            "f91", "f92", "f93", "f94", "f95", "f96", "f97", "f98", "f99", "f100",
            "f101", "f102", "f103", "f104", "f105", "f106", "f107", "f108", "f109"
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_phrase_for_day)

        phraseTextView = findViewById(R.id.text_phrase)
        textOnButton = findViewById(R.id.text_on_button)

        val scrollClosed: ImageView = findViewById(R.id.scrollClosed)
        val scrollOpened: ImageView = findViewById(R.id.scrollOpened)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        toolbar.setNavigationOnClickListener { onBackPressed() }

        val sharedPrefs = getSharedPreferences("PhraseForDay", Context.MODE_PRIVATE)
        val savedPhraseKey = sharedPrefs.getString("phraseText", null)

        savedPhraseKey?.let {
            val resId = resources.getIdentifier(it, "string", packageName)
            phraseTextView.text = if (resId != 0) getString(resId) else ""
        }

        if (isBoxOpenableToday()) {
            val visibility = sharedPrefs.getBoolean("svitokClose", false)
            if (visibility) {
                phraseTextView.visibility = View.GONE
                textOnButton.visibility = View.VISIBLE
                scrollClosed.visibility = View.VISIBLE
                scrollOpened.visibility = View.GONE
            }
            scrollClosed.setOnClickListener {
                scrollClosed.animate()
                    .scaleX(0f)
                    .scaleY(0f)
                    .alpha(0f)
                    .setDuration(500)
                    .setListener(object : Animator.AnimatorListener {

                        override fun onAnimationStart(p0: Animator) {
                            scrollClosed.visibility = View.GONE
                            scrollOpened.visibility = View.VISIBLE
                            scrollOpened.alpha = 0f
                            scrollOpened.scaleX = 0f
                            scrollOpened.scaleY = 0f
                            scrollOpened.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .alpha(1f)
                                .setDuration(500)
                                .start()
                        }

                        override fun onAnimationEnd(p0: Animator) {}
                        override fun onAnimationCancel(p0: Animator) {}
                        override fun onAnimationRepeat(p0: Animator) {}
                    })
                    .start()

                textOnButton.visibility = View.GONE
                showRandomPhrase()
                saveCurrentDateAsLastOpenDate()
            }
        } else {
            textOnButton.visibility = View.GONE
            scrollClosed.visibility = View.GONE
            scrollOpened.visibility = View.VISIBLE
        }
    }

    private fun showRandomPhrase() {
        val randomPhrase = phrases.random()
        phraseTextView.visibility = View.VISIBLE
        phraseTextView.text = getString(resources.getIdentifier(randomPhrase, "string", packageName))
        val sharedPrefs = getSharedPreferences("PhraseForDay", Context.MODE_PRIVATE)
        sharedPrefs.edit().putString("phraseText", randomPhrase).apply()
        sharedPrefs.edit().putBoolean("svitokClose", true).apply()
    }

    private fun isBoxOpenableToday(): Boolean {
        val sharedPrefs = getSharedPreferences("PhraseForDay", Context.MODE_PRIVATE)
        val lastOpenDate = sharedPrefs.getString("lastOpenDate", null)
        val currentDate = getCurrentDate()
        return lastOpenDate != currentDate
    }

    private fun saveCurrentDateAsLastOpenDate() {
        val sharedPrefs = getSharedPreferences("PhraseForDay", Context.MODE_PRIVATE)
        with(sharedPrefs.edit()) {
            putString("lastOpenDate", getCurrentDate())
            apply()
        }
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Date())
    }
}