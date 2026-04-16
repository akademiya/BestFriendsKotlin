package com.vadym.gvd.bestfriendskotlin.shimjeong_shop

import android.content.Context

class CoinManager(context: Context) {

    private val prefs = context.getSharedPreferences("shimjeong_coins", Context.MODE_PRIVATE)
    private val purchasedCache = mutableSetOf<Int>()

    companion object {
        const val COINS_FOR_PHRASE          = 5
        const val COINS_PHRASE_STREAK_BONUS = 7
        const val PHRASE_STREAK_DAYS        = 7
        const val COINS_FOR_RATING          = 5
        const val COINS_PER_HDH_DAY         = 7
        const val HDH_STREAK_DAYS           = 7
        const val COINS_HDH_STREAK_BONUS    = 7
        const val COIN_EASTER_EGG           = 1
        const val COINS_FOR_CONDITION       = 10

        private const val KEY_BALANCE       = "balance"
        private const val KEY_PURCHASED     = "purchased_cards"
        private const val KEY_STREAK_COUNT  = "streak_count"
        private const val KEY_STREAK_DATE   = "streak_last_date"
        private const val KEY_RATED         = "app_rated"
        private const val KEY_HDH_STREAK_COUNT = "hdh_streak_count"
        private const val KEY_HDH_STREAK_DATE  = "hdh_streak_last_date"
    }

    init {
        purchasedCache.addAll(getPurchasedIds())
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
     * Повертає true якщо досягнуто [PHRASE_STREAK_DAYS] днів підряд — щоб UI показав бонус.
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
        if (newStreak % PHRASE_STREAK_DAYS == 0) {
            addCoins(COINS_PHRASE_STREAK_BONUS)
            return true
        }
        return false
    }

    fun purchaseCard(cardId: Int): Boolean {
        if (isCardPurchased(cardId)) return false
        val card = ShopCard.all.find { it.id == cardId } ?: return false
        if (!spendCoins(card.price)) return false

        purchasedCache.add(cardId)
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
        if (prefs.getBoolean(KEY_RATED, false)) return false
        prefs.edit()
            .putBoolean(KEY_RATED, true)
            .apply()
        addCoins(COINS_FOR_RATING)
        return true
    }

    fun rewardForHDHMonth(yearMonth: String): Boolean {
        val key = "hdh_reward_$yearMonth"
        if (prefs.getBoolean(key, false)) return false
        prefs.edit().putBoolean(key, true).apply()
        addCoins(20)
        return true
    }

    fun recordHDHDay(today: String): Boolean {
        val lastDate  = prefs.getString(KEY_HDH_STREAK_DATE, null)
        val yesterday = getYesterday(today)

        val newStreak = when (lastDate) {
            today     -> return false              // вже зараховано сьогодні
            yesterday -> (prefs.getInt(KEY_HDH_STREAK_COUNT, 0) + 1) // серія продовжується
            else      -> 1                         // пропуск або перший день — скидаємо
        }

        val bonusReached = newStreak >= HDH_STREAK_DAYS

        prefs.edit()
            .putInt(KEY_HDH_STREAK_COUNT, if (bonusReached) 0 else newStreak)
            .putString(KEY_HDH_STREAK_DATE, today)
            .apply()

        if (bonusReached) {
            addCoins(COINS_HDH_STREAK_BONUS)
            return true
        }
        return false
    }

    val currentHDHStreak: Int
        get() = prefs.getInt(KEY_HDH_STREAK_COUNT, 0)
}