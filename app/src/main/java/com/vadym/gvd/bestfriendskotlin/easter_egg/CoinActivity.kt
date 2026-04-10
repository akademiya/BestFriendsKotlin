package com.vadym.gvd.bestfriendskotlin.easter_egg

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.shimjeong_shop.CoinManager

abstract class CoinActivity : MainActivity() {

    protected val coinViewModel: CoinViewModel by viewModels()
    private lateinit var coinManager: CoinManager
    abstract val coinViewMap: Map<String, Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coinManager = CoinManager(this)

        coinViewModel.todayCoinLive.observe(this) { todayId ->
            if (todayId.isNullOrEmpty()) return@observe
            val viewId   = coinViewMap[todayId] ?: return@observe
            val coinView = findViewById<ImageView>(viewId) ?: return@observe

            if (coinViewModel.isCoinCollected(todayId)) {
                coinView.visibility = View.GONE
                return@observe
            }

            coinView.visibility = View.VISIBLE
            coinView.setOnClickListener { animateAndCollect(coinView, todayId) }
        }
    }

    override fun onResume() {
        super.onResume()
        val todayId = coinViewModel.todayCoinId
        if (todayId.isEmpty()) return
        val viewId   = coinViewMap[todayId] ?: return
        val coinView = findViewById<ImageView>(viewId) ?: return
        if (coinViewModel.isCoinCollected(todayId)) {
            coinView.visibility = View.GONE
        }
    }

//    private fun checkAndShowDailyCoin() {
//        val todayId = coinViewModel.todayCoinId
//        val viewId  = coinViewMap[todayId] ?: return  // монета не в цьому activity
//
//        val coinView = findViewById<ImageView>(viewId) ?: return
//        if (coinViewModel.isCoinCollected(todayId)) {
//            coinView.visibility = View.GONE
//            return
//        }
//        coinView.visibility = View.VISIBLE
//        coinView.setOnClickListener { animateAndCollect(coinView, todayId) }
//    }

    private fun animateAndCollect(view: ImageView, coinId: String) {
        val mediaPlayer = MediaPlayer.create(this, R.raw.sj_coin)
        mediaPlayer.setOnCompletionListener { it.release() }
        mediaPlayer.start()

        coinManager.addCoins(CoinManager.COIN_EASTER_EGG)
        coinViewModel.collectCoin(coinId)
        Snackbar.make(view, "🪙 +1 SC монета знайдена!", Snackbar.LENGTH_SHORT).show()


        // Обертання по Y (як справжня монета)
        val flip = ObjectAnimator.ofFloat(view, "rotationY", 0f, 360f, 720f).apply {
            duration = 800
            interpolator = AccelerateInterpolator()
        }

        // Вліт вгору вправо
        val moveX = ObjectAnimator.ofFloat(view, "translationX", 0f, 400f).apply {
            duration = 900
            interpolator = AccelerateInterpolator()
        }
        val moveY = ObjectAnimator.ofFloat(view, "translationY", 0f, -600f).apply {
            duration = 900
            interpolator = DecelerateInterpolator()
        }

        // Зникнення
        val fade = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f).apply {
            duration = 900
            startDelay = 300
            interpolator = AccelerateInterpolator()
        }

        // Збільшення на старті (ефект підстрибування)
        val scaleUp = ObjectAnimator.ofPropertyValuesHolder(
            view,
            PropertyValuesHolder.ofFloat("scaleX", 1f, 1.4f, 1f),
            PropertyValuesHolder.ofFloat("scaleY", 1f, 1.4f, 1f)
        ).apply { duration = 300 }

        AnimatorSet().apply {
            play(scaleUp)
            play(flip).after(scaleUp)
            play(moveX).after(scaleUp)
            play(moveY).after(scaleUp)
            play(fade).after(scaleUp)
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    view.visibility = View.GONE
                    // Скидаємо трансформації якщо view буде reused
                    view.translationX = 0f
                    view.translationY = 0f
                    view.alpha = 1f
                    view.scaleX = 1f
                    view.scaleY = 1f
                    view.rotationY = 0f
                }
            })
            start()
        }
    }
}