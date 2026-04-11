package com.vadym.gvd.bestfriendskotlin.traditions

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.ads.AdView
import com.vadym.gvd.bestfriendskotlin.AdManager
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.quiz.QuizView
import com.vadym.gvd.bestfriendskotlin.toHtml
import com.vadym.gvd.bestfriendskotlin.treelife.ReadArticleTask
import com.vadym.gvd.bestfriendskotlin.treelife.TreeLevel
import com.vadym.gvd.bestfriendskotlin.treelife.TreeOfLifeDB
import com.vadym.gvd.bestfriendskotlin.treelife.TreeTasksConfig

class GeneralViewContent : MainActivity() {

    private lateinit var ARTICLE_KEYS: Map<Int, String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_general_traditions_content)
        toolbarButtonMenu()
        val adContainer: AdView = findViewById(R.id.adViewTraditionsContent)
        val adDivider: View = findViewById(R.id.adDivider)
        val traditionTitle: TextView = findViewById(R.id.tradition_title)
        val traditionDescription: TextView = findViewById(R.id.tradition_description)
        val traditionsImage: ImageView = findViewById(R.id.traditions_img)
        val taskCompletedBtn = findViewById<Button>(R.id.task_completed)
        val quizBtn = findViewById<Button>(R.id.go_to_quiz)

        ARTICLE_KEYS = mapOf(
            0 to getString(R.string.sunday_service),     //stage 3
            1 to getString(R.string.hdh),                //stage 2
            2 to getString(R.string.pledge),             //stage 1
            3 to getString(R.string.anshiil),            //stage 4
            8 to getString(R.string.life_service),       //stage 5
            10 to getString(R.string.prayer_tradition),  //stage 6
            13 to getString(R.string.salt)               //stage 7
        )


        AdManager.setupBanner(this, adContainer, adDivider)

        traditionTitle.text = intent.getStringExtra("TRADITION_TITLE") ?: "Unknown Tradition"
        val position = intent.getIntExtra("TRADITION_POSITION", 0)

        val spannable = SpannableStringBuilder()
        listOf(
            R.string.pledge_description1,
            R.string.pledge_description2,
            R.string.pledge_description3,
            R.string.pledge_description4,
            R.string.pledge_description5,
            R.string.pledge_description6,
            R.string.pledge_description7,
            R.string.pledge_description8
        ).forEach { resId ->
            spannable.append(getString(resId).toHtml())
        }

        when(position) {
            0 -> traditionDescription.text = getString(R.string.sunday_service_description)
            1 -> traditionDescription.text = getString(R.string.hdh_description).toHtml()
            2 -> traditionDescription.text = spannable
            3 -> traditionDescription.text = getString(R.string.anshiil_description).toHtml()
            4 -> traditionDescription.text = getString(R.string.photo_of_tp_description)
            5 -> traditionDescription.text = getString(R.string.days8_description).toHtml()
            6 -> traditionDescription.text = getString(R.string.birthday_description).toHtml()
            7 -> traditionDescription.text = getString(R.string.clothes_description)
            8 -> traditionDescription.text = getString(R.string.life_service_description).toHtml()
            9 -> traditionDescription.text = getString(R.string.songhwa_description).toHtml()
            10 -> traditionDescription.text = getString(R.string.prayer_2g_description).toHtml()
            12 -> traditionDescription.text = getString(R.string.desyatyna_description).toHtml()
            13 -> {
                traditionDescription.text = getString(R.string.salt_description).toHtml()
                traditionsImage.visibility = View.VISIBLE
                traditionsImage.setImageResource(R.drawable.tr_salt)
            }
            14 -> {
                traditionDescription.text = getString(R.string.vine_description).toHtml()
                traditionsImage.visibility = View.VISIBLE
                traditionsImage.setImageResource(R.drawable.tr_vine)
            }
            15 -> traditionDescription.text = getString(R.string.candle_description).toHtml()
        }

        // ── Кнопка завдання + quiz button ───────────────────────────────────────────────────
        val articleKey = ARTICLE_KEYS[position]

        if (articleKey != null) {
            val tree         = TreeOfLifeDB.getInstance(this).getTree()
            val currentStage = tree?.stageIndividual ?: 1

            // Чи є цей articleKey активним завданням на поточній стадії?
            val isActiveTask = TreeTasksConfig
                .tasksFor(TreeLevel.INDIVIDUAL, currentStage, this)
                .filterIsInstance<ReadArticleTask>()
                .any { it.articleKey == articleKey }

            val prefs       = getSharedPreferences("articles_read", MODE_PRIVATE)
            val prefKey     = "read_$articleKey"
            val alreadyRead = prefs.getBoolean(prefKey, false)

            when {
                alreadyRead -> {
                    taskCompletedBtn.visibility = View.VISIBLE
                    taskCompletedBtn.isEnabled  = false
                    taskCompletedBtn.text       = getString(R.string.task_already_completed)
                }
                isActiveTask -> {
                    taskCompletedBtn.visibility = View.VISIBLE
                    taskCompletedBtn.isEnabled  = true
                    taskCompletedBtn.text       = getString(R.string.task_completed)

                    taskCompletedBtn.setOnClickListener {
                        prefs.edit().putBoolean(prefKey, true).apply()
                        taskCompletedBtn.isEnabled = false
                        taskCompletedBtn.text      = getString(R.string.task_already_completed)
                    }
                }
                else -> taskCompletedBtn.visibility = View.GONE
            }
        } else {
            taskCompletedBtn.visibility = View.GONE
        }


        // ── Quiz кнопка ───────────────────────────────────────────────────────
        val quizKey = articleKey  // той самий ключ що і для статті

        if (quizKey != null) {
            val quizPassed  = getSharedPreferences("quizzes_passed", MODE_PRIVATE)
                .getBoolean("quiz_$quizKey", false)
            val articleRead = getSharedPreferences("articles_read", MODE_PRIVATE)
                .getBoolean("read_$quizKey", false)

            when {
                quizPassed -> {
                    quizBtn.visibility = View.VISIBLE
                    quizBtn.isEnabled  = false
                    quizBtn.text       = getString(R.string.quiz_already_passed)
                }
                articleRead -> {
                    // Статтю прочитано → quiz доступна
                    quizBtn.visibility = View.VISIBLE
                    quizBtn.isEnabled  = true
                    quizBtn.text       = getString(R.string.go_to_quiz)
                    quizBtn.setOnClickListener {
                        QuizView.start(this, quizKey)
                    }
                }
                else -> quizBtn.visibility = View.GONE
            }
        } else {
            quizBtn.visibility = View.GONE
        }

    }

    private fun toolbarButtonMenu() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}