package com.vadym.gvd.bestfriendskotlin.shimjeong_shop

import android.content.Context

class CoinManager(context: Context) {

    private val prefs = context.getSharedPreferences("shimjeong_coins", Context.MODE_PRIVATE)

    companion object {
        const val COINS_PER_DAY    = 5
        const val COINS_STREAK_BONUS = 20
        const val STREAK_DAYS      = 7
        const val COINS_FOR_RATING   = 5

        private const val KEY_BALANCE       = "balance"
        private const val KEY_PURCHASED     = "purchased_cards"
        private const val KEY_STREAK_COUNT  = "streak_count"
        private const val KEY_STREAK_DATE   = "streak_last_date"
        private const val KEY_RATED         = "app_rated"
    }

    val balance: Int
        get() = prefs.getInt(KEY_BALANCE, 0)

    val currentStreak: Int
        get() = prefs.getInt(KEY_STREAK_COUNT, 0)

    fun addCoins(amount: Int) {
        prefs.edit().putInt(KEY_BALANCE, balance + amount).apply()
    }

    fun spendCoins(amount: Int): Boolean {
        if (balance < amount) return false
        prefs.edit().putInt(KEY_BALANCE, balance - amount).apply()
        return true
    }

    /**
     * Викликати щоразу коли користувач відкриває фразу.
     * Повертає true якщо досягнуто [STREAK_DAYS] днів підряд — щоб UI показав бонус.
     */
    fun recordDailyOpen(today: String): Boolean {
        val lastDate   = prefs.getString(KEY_STREAK_DATE, null)
        val yesterday  = getYesterday(today)

        val newStreak = when (lastDate) {
            today     -> return false          // вже зараховано сьогодні
            yesterday -> currentStreak + 1     // серія продовжується
            else      -> 1                     // серія скидається
        }

        prefs.edit()
            .putInt(KEY_STREAK_COUNT, newStreak)
            .putString(KEY_STREAK_DATE, today)
            .apply()

        // Бонус кожні STREAK_DAYS днів
        if (newStreak % STREAK_DAYS == 0) {
            addCoins(COINS_STREAK_BONUS)
            return true
        }
        return false
    }

    fun purchaseCard(cardId: Int): Boolean {
        val card = ShopCard.all.find { it.id == cardId } ?: return false
        if (!spendCoins(card.price)) return false
        val purchased = getPurchasedIds().toMutableSet()
        purchased.add(cardId)
        prefs.edit().putStringSet(KEY_PURCHASED, purchased.map { it.toString() }.toSet()).apply()
        return true
    }

    fun isCardPurchased(cardId: Int): Boolean =
        getPurchasedIds().contains(cardId)

    private fun getPurchasedIds(): Set<Int> =
        prefs.getStringSet(KEY_PURCHASED, emptySet())
            ?.mapNotNull { it.toIntOrNull() }
            ?.toSet() ?: emptySet()

    private fun getYesterday(today: String): String {
        val sdf  = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
        val cal  = java.util.Calendar.getInstance()
        cal.time = sdf.parse(today)!!
        cal.add(java.util.Calendar.DAY_OF_YEAR, -1)
        return sdf.format(cal.time)
    }

    fun rewardForRating(): Boolean {
        if (prefs.getBoolean(KEY_RATED, false)) return false  // вже нараховано
        prefs.edit()
            .putBoolean(KEY_RATED, true)
            .apply()
        addCoins(COINS_FOR_RATING)
        return true
    }
}