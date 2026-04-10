package com.vadym.gvd.bestfriendskotlin.easter_egg

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences("daily_coin", Context.MODE_PRIVATE)
    private val repo  = CoinRepository(CoinDatabase.getInstance(application).coinDao())

    val collectedCount: LiveData<Int> = repo.collectedCount

    // ID монети дня — береться з SharedPreferences (вже закешовано WorkManager'ом)
    val todayCoinId: String
        get() = prefs.getString("today_coin_id", "") ?: ""

    private val _todayCoinLive = MutableLiveData<String>()
    val todayCoinLive: LiveData<String> = _todayCoinLive

    fun isCoinCollected(coinId: String): Boolean {
        return prefs.getBoolean("collected_$coinId", false)
    }

    init {
        _todayCoinLive.value = todayCoinId
        viewModelScope.launch {
            repo.pickDailyCoinIfNeeded(prefs)
            _todayCoinLive.postValue(todayCoinId)
        }
    }

    fun collectCoin(coinId: String) = viewModelScope.launch {
        prefs.edit().putBoolean("collected_$coinId", true).apply()
        repo.collectCoin(coinId)
        _todayCoinLive.postValue("")
    }
}