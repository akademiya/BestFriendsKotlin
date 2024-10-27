package com.vadym.gvd.bestfriendskotlin

import android.annotation.SuppressLint
import android.app.Application
import com.google.android.gms.analytics.GoogleAnalytics
import com.google.android.gms.analytics.Tracker


@SuppressLint("Registered")
class AndroidApplication : Application() {
//    private lateinit var sAnalytics: GoogleAnalytics
//    private lateinit var sTracker: Tracker

//    override fun onCreate() {
//        super.onCreate()
//        sAnalytics = GoogleAnalytics.getInstance(this)
//        sTracker = sAnalytics.newTracker(R.xml.global_tracker)
//    }

    /**
     * Gets the default [Tracker] for this [Application].
     * @return tracker
     */
//    @Synchronized
//    fun getDefaultTracker(): Tracker {
//        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
//        return sTracker
//    }
}