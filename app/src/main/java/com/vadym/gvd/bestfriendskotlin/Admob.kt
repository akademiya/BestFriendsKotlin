package com.vadym.gvd.bestfriendskotlin
import android.content.Context
import android.util.DisplayMetrics
import android.view.ViewGroup
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

object Admob {

    fun initializeAdmob(context: Context, adContainer: AdView) {
        MobileAds.initialize(context) {}

        val adRequest = AdRequest.Builder().build()
        adContainer.loadAd(adRequest)
    }
}