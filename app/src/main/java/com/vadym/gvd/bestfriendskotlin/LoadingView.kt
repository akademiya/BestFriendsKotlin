package com.vadym.gvd.bestfriendskotlin

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.method.LinkMovementMethod
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView

class LoadingView : AppCompatActivity() {

    private val DAYS_UNTIL_PROMPT = 90L //Min number of days
    private lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_loading)

        val prefs = getSharedPreferences("gdpr", 0)
        val editor = prefs.edit()

        // Get date of first launch
        var dateFirstLaunch: Long = prefs.getLong("date_firstlaunch", 0)
        if (dateFirstLaunch == 0L) {
            dateFirstLaunch = System.currentTimeMillis()
            editor.putLong("date_firstlaunch", dateFirstLaunch)
            showMyConsentDialog(editor)
        } else {
            // Wait at least n days before opening
            if (System.currentTimeMillis() >= dateFirstLaunch + DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000) {
                showMyConsentDialog(editor)
            } else {
                editor.commit()
                startUseApp()
            }
        }
    }

    private fun showMyConsentDialog(editor: SharedPreferences.Editor?) {
        val inflater = layoutInflater
        val consentDialog = inflater.inflate(R.layout.view_loading_consent, null)
        val alertDialogBuilder = AlertDialog.Builder(this@LoadingView).apply {
            setView(consentDialog)
            setCancelable(false)
        }

        alertDialog = alertDialogBuilder.create()
        alertDialog.show()

        val learnMore = consentDialog.findViewById<TextView>(R.id.tv_eu_learn_more)
        val cbAgree = consentDialog.findViewById<CheckBox>(R.id.cb_accept_gdpr)
        val iAgree = consentDialog.findViewById<Button>(R.id.btn_agree)

        learnMore.movementMethod = LinkMovementMethod.getInstance()
        cbAgree.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                iAgree.isEnabled = true
                cbAgree.text = resources.getString(R.string.agree)
                cbAgree.setTextColor(Color.BLACK)
            } else {
                iAgree.isEnabled = false
                cbAgree.text = resources.getString(R.string.error_accept)
                cbAgree.setTextColor(Color.RED)
            }
        }
        iAgree.setOnClickListener {
            if (editor != null) {
                editor.putLong("date_firstlaunch", System.currentTimeMillis())
                editor.commit()
            }
            alertDialog.cancel()
            startUseApp()
        }
    }

    private fun startUseApp() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}