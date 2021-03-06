package com.vadym.gvd.bestfriendskotlin.kido

import android.os.Handler
import android.os.Looper
import java.util.*

object Chronometer {
    var base: Long = 0
    var nextBeep: Long = 0

    private lateinit var tickListener: () -> Unit
    private lateinit var scheduledTask: TimerTask
    private var justOneStart = false
    private val timer = Timer()

    fun setOnTick(whatIDo: () -> Unit) {
        tickListener = whatIDo
    }

    fun start() {
        if (!justOneStart) {
            scheduledTask = object : TimerTask() {
                override fun run() {
                    Handler(Looper.getMainLooper()).post {
                        tickListener()
                    }
                }
            }
            timer.scheduleAtFixedRate(scheduledTask, 1000, 1000)
            justOneStart = true
        } else return
    }

    fun stop() {
        if (base > 0)
            scheduledTask.cancel()
        else doNothing()
        nextBeep = 0
        justOneStart = false
    }

    fun doNothing() {}

}