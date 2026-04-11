package com.vadym.gvd.bestfriendskotlin.quiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import java.time.LocalDate

class QuizView : MainActivity() {

    companion object {
        const val EXTRA_QUIZ_KEY = "quiz_key"

        fun start(ctx: Context, quizKey: String) {
            ctx.startActivity(Intent(ctx, QuizView::class.java).apply {
                putExtra(EXTRA_QUIZ_KEY, quizKey)
            })
        }
    }

    private lateinit var quizKey: String
    private lateinit var questions: List<QuizQuestion>
    private var currentIndex = 0
    private var correctCount = 0
    private var selectedAnswer: Int? = null

    private lateinit var tvProgress: TextView
    private lateinit var tvQuestion: TextView
    private lateinit var rvAnswers: RecyclerView
    private lateinit var btnCheck: Button
    private lateinit var adapter: AnswerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_quiz)

        quizKey   = intent.getStringExtra(EXTRA_QUIZ_KEY) ?: return finish()
        questions = QuizRepository.getQuestions(quizKey, this)

        if (questions.isEmpty()) {
            Toast.makeText(this, getString(R.string.question_not_found), Toast.LENGTH_SHORT).show()
            finish(); return
        }

        if (isCoolingDown()) {
            showCooldownDialog(); return
        }

        tvProgress = findViewById(R.id.tv_quiz_progress)
        tvQuestion = findViewById(R.id.tv_quiz_question)
        rvAnswers  = findViewById(R.id.rv_quiz_answers)
        btnCheck   = findViewById(R.id.btn_quiz_check)

        setupToolbar()
        setupBackPress()
        showQuestion(currentIndex)

        btnCheck.setOnClickListener { onCheckClicked() }
    }

    // ─── Питання ──────────────────────────────────────────────────────────────

    private fun showQuestion(index: Int) {
        selectedAnswer = null
        btnCheck.isEnabled = false

        val q = questions[index]
        tvProgress.text = "${index + 1} / ${questions.size}"
        tvQuestion.text = q.text

        adapter = AnswerAdapter(q.answers) { selectedIndex ->
            selectedAnswer   = selectedIndex
            btnCheck.isEnabled = true
        }
        rvAnswers.layoutManager = LinearLayoutManager(this)
        rvAnswers.adapter       = adapter
    }

    private fun onCheckClicked() {
        val selected = selectedAnswer ?: return
        val q        = questions[currentIndex]
        val isCorrect = selected == q.correctIndex

        if (isCorrect) correctCount++

        adapter.revealAnswer(selected, q.correctIndex)
        btnCheck.isEnabled = false
        currentIndex++

        // Затримка + анімація fade перед наступним питанням
        rvAnswers.postDelayed({
            if (currentIndex < questions.size) {
                tvQuestion.animate().alpha(0f).setDuration(150).withEndAction {
                    showQuestion(currentIndex)
                    tvQuestion.animate().alpha(1f).setDuration(150).start()
                }.start()
            } else {
                finishQuiz()
            }
        }, 900)
    }

    // ─── Результат ────────────────────────────────────────────────────────────

    private fun finishQuiz() {
        val passed = correctCount >= 8
        val prefs  = getSharedPreferences("quizzes_passed", Context.MODE_PRIVATE)
        val today  = LocalDate.now().toString()

        if (passed) {
            prefs.edit().putBoolean("quiz_$quizKey", true).apply()
        } else {
            prefs.edit().putString("quiz_cooldown_$quizKey", today).apply()
        }

        val msg = if (passed)
            getString(R.string.quiz_passed, correctCount, questions.size)
        else
            getString(R.string.quiz_failed, correctCount, questions.size)

        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        finish()
    }

    // ─── Cooldown ─────────────────────────────────────────────────────────────

    private fun isCoolingDown(): Boolean {
        val prefs     = getSharedPreferences("quizzes_passed", Context.MODE_PRIVATE)
        val alreadyPassed = prefs.getBoolean("quiz_$quizKey", false)
        if (alreadyPassed) return false   // вже пройдено — не блокуємо

        val cooldownDate = prefs.getString("quiz_cooldown_$quizKey", null) ?: return false
        return cooldownDate == LocalDate.now().toString()
    }

    private fun showCooldownDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.dialog_try_tomorrow))
            .setMessage(getString(R.string.dialog_back_tomorrow))
            .setPositiveButton("OK") { _, _ -> finish() }
            .setCancelable(false)
            .show()
    }

    // ─── Navigation ───────────────────────────────────────────────────────────

    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { finish() }
    }

    private fun setupBackPress() {
        onBackPressedDispatcher.addCallback(this) { finish() }
    }
}