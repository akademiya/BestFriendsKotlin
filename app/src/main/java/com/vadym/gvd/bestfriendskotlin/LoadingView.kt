package com.vadym.gvd.bestfriendskotlin

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.text.method.LinkMovementMethod
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat

class LoadingView : AppCompatActivity() {

    companion object {
        private const val PREFS_NAME        = "gdpr"
        private const val KEY_CONSENT_GIVEN = "consent_given"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_loading)

        if (isConsentGiven()) {
            // Згода вже є — ініціалізуємо AdMob і показуємо рекламу перед входом
            initAdAndProceed()
        } else {
            // Перший запуск — спочатку отримуємо згоду, реклама — після
            showConsentDialog()
        }
    }

    // ── GDPR ─────────────────────────────────────────────────────────────────

    private fun isConsentGiven(): Boolean =
        getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
            .getBoolean(KEY_CONSENT_GIVEN, false)

    private fun saveConsent() {
        getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
            .edit().putBoolean(KEY_CONSENT_GIVEN, true).apply()
    }

    private fun showConsentDialog() {
        val consentView = layoutInflater.inflate(R.layout.view_loading_consent, null)

        val dialog = AlertDialog.Builder(this)
            .setView(consentView)
            .setCancelable(false)
            .create()

        dialog.show()

        val tvLearnMore = consentView.findViewById<TextView>(R.id.tv_eu_learn_more)
        val cbAgree     = consentView.findViewById<CheckBox>(R.id.cb_accept_gdpr)
        val btnAgree    = consentView.findViewById<Button>(R.id.btn_agree)

        tvLearnMore.movementMethod = LinkMovementMethod.getInstance()

        cbAgree.setOnCheckedChangeListener { _, isChecked ->
            btnAgree.isEnabled = isChecked
            if (isChecked) {
                cbAgree.setText(R.string.agree)
                cbAgree.setTextColor(ContextCompat.getColor(this, R.color.cb_agree))
            } else {
                cbAgree.setText(R.string.error_accept)
                cbAgree.setTextColor(Color.RED)
            }
        }

        btnAgree.setOnClickListener {
            saveConsent()
            dialog.dismiss()
            // Тільки після згоди — ініціалізуємо AdMob (вимога Google / GDPR)
            initAdAndProceed()
        }
    }

    // ── Реклама → перехід ─────────────────────────────────────────────────────

    /**
     * Ініціалізує AdMob, потім намагається показати рекламу.
     * Після закриття реклами (або якщо її нема) — переходить в MainActivity.
     *
     * ВАЖЛИВО: onFinished = { startUseApp() } викликається ЗАВЖДИ —
     * навіть якщо реклама не завантажилась або не показалась.
     */
    private fun initAdAndProceed() {
        AdManager.init(this)
        AdManager.tryShowOnAppStart(this) {
            startUseApp()
        }
    }

    // ── Навігація ─────────────────────────────────────────────────────────────

    private fun startUseApp() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}