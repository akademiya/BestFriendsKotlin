package com.vadym.gvd.bestfriendskotlin

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.method.LinkMovementMethod
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import com.google.ads.consent.ConsentInfoUpdateListener
import com.google.ads.consent.ConsentInformation
import com.google.ads.consent.ConsentStatus

class LoadingView: AppCompatActivity() {

    private lateinit var alertDialog: AlertDialog
    private val presenter = MainPresenter
    private var showNonPersonalizedAdRequests = false
    private var isPrivacyPolicy = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_loading)


        if (presenter.isPrivacyPolicy) {
            ConsentInformation.getInstance(this@LoadingView).consentStatus = ConsentStatus.NON_PERSONALIZED
            startActivity(Intent(this, MainActivity::class.java))
        } else checkConsentStatus()
    }

    private fun checkConsentStatus() {
        val consentInformation = ConsentInformation.getInstance(this)
        ConsentInformation.getInstance(this)

        val publisherIds = arrayOf("pub-5169531562006723") // enter your admob pub-id
        consentInformation.requestConsentInfoUpdate(publisherIds, object : ConsentInfoUpdateListener {
            override fun onConsentInfoUpdated(consentStatus: ConsentStatus) {
//                if (ConsentInformation.getInstance(this@LoadingView).isRequestLocationInEeaOrUnknown) {
                    if (consentStatus == ConsentStatus.UNKNOWN) {
                        showMyConsentDialog()
                    } else if (consentStatus == ConsentStatus.NON_PERSONALIZED) {
                        showMyConsentDialog()
                        showNonPersonalizedAdRequests = true
                    }
//                }
            }

            override fun onFailedToUpdateConsentInfo(errorDescription: String) {}
        })
    }

    fun showMyConsentDialog() {
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
            ConsentInformation.getInstance(this@LoadingView).consentStatus = ConsentStatus.NON_PERSONALIZED
            showNonPersonalizedAdRequests = true
            presenter.updatePrivacyPolicy(true)
            alertDialog.cancel()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

//    private fun onRequestAdStatus() {
//        val extras = Bundle()
//        extras.putString("npa", "1")
//
//        val request = PublisherAdRequest.Builder()
//                .addNetworkExtrasBundle(AdMobAdapter::class.java, extras)
//                .build()
//    }

}