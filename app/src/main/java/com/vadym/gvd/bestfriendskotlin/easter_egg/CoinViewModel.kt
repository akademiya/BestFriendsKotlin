package com.vadym.gvd.bestfriendskotlin.easter_egg

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences("daily_coin", Context.MODE_PRIVATE)
    private val repo  = CoinRepository(CoinDatabase.getInstance(application).coinDao())

    val collectedCount: LiveData<Int> = repo.collectedCount

    // ID монети дня — береться з SharedPreferences (вже закешовано WorkManager'ом)
    val todayCoinId: String
        get() = prefs.getString("today_coin_id", "") ?: ""

    fun collectCoin(coinId: String) = viewModelScope.launch {
        repo.collectCoin(coinId)
    }
}