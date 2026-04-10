package com.vadym.gvd.bestfriendskotlin.easter_egg

import android.content.SharedPreferences
import java.time.LocalDate

class CoinRepository(private val dao: CoinDao) {

//    val allCoins = dao.getAllCoins()
    val collectedCount = dao.getCollectedCount()

    // Викликається при першому запуску — сіємо всі 100 монет
    suspend fun seedCoins() {
        val existing = dao.getAllCoinsSync()
        if (existing.size >= CoinSeeds.ALL_COINS.size) return
        dao.insertAll(CoinSeeds.ALL_COINS)
    }

    // WorkManager викликає щодня
    suspend fun pickDailyCoinIfNeeded(prefs: SharedPreferences) {
        val today = LocalDate.now().toString()
        val lastDate = prefs.getString("last_coin_date", "")

        if (lastDate == today) return  // вже вибрана сьогодні

        // Скидаємо вчорашню монету якщо не зібрана
        val yesterday = dao.getTodayCoin()
        yesterday?.let { dao.updateCoin(it.copy(isVisibleToday = false)) }

        // Вибираємо нову рандомну незібрану монету
        val uncollected = dao.getUncollectedCoins()
        if (uncollected.isEmpty()) return

        val chosen = uncollected.random()
        dao.updateCoin(chosen.copy(isVisibleToday = true, lastShownDate = today))

        prefs.edit()
            .putString("last_coin_date", today)
            .putString("today_coin_id", chosen.id)
            .apply()
    }

    suspend fun collectCoin(coinId: String) {
        val coin = dao.getUncollectedCoins().find { it.id == coinId } ?: return
        dao.updateCoin(coin.copy(isCollected = true, isVisibleToday = false))
    }
}