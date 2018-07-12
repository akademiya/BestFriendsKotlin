package com.example.user.bestfriendskotlin.kido

import android.os.Handler
import android.os.Looper
import java.util.*

object Chronometer {
    var base: Long = 0
    var nextBeep: Long = 0

    private lateinit var tickListener: () -> Unit
    private lateinit var scheduledTask: TimerTask
    private val timer = Timer()

    fun setOnTick(whatIDo: () -> Unit) {
        tickListener = whatIDo
    }

    fun start() {
        scheduledTask = object : TimerTask() {
            override fun run() {
                Handler(Looper.getMainLooper()).post {
                    tickListener()
                }
            }
        }
        timer.scheduleAtFixedRate(scheduledTask, 1000, 1000)
    }

    fun stop() {
        scheduledTask.cancel()
        nextBeep = 0
    }

}