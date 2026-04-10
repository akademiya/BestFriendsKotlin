package com.vadym.gvd.bestfriendskotlin.easter_egg

import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.vadym.gvd.bestfriendskotlin.MainActivity

abstract class CoinActivity : MainActivity() {

    protected val coinViewModel: CoinViewModel by viewModels()

    // Кожне activity оголошує свій тег і Map монет
    abstract val coinViewMap: Map<String, Int>  // coinId -> viewResId

    override fun onResume() {
        super.onResume()
        checkAndShowDailyCoin()
    }

    private fun checkAndShowDailyCoin() {
        val todayId = coinViewModel.todayCoinId
        val viewId  = coinViewMap[todayId] ?: return  // монета не в цьому activity

        val coinView = findViewById<ImageView>(viewId) ?: return
        coinView.visibility = View.VISIBLE
        coinView.setOnClickListener { animateAndCollect(coinView, todayId) }
    }

    private fun animateAndCollect(view: ImageView, coinId: String) {
        coinViewModel.collectCoin(coinId)
        view.animate()
            .alpha(0f).scaleX(0f).scaleY(0f)
            .setDuration(500)
            .withEndAction { view.visibility = View.GONE }
            .start()
        Snackbar.make(view, "🪙 +1 SC монета знайдена!", Snackbar.LENGTH_SHORT).show()
    }
}