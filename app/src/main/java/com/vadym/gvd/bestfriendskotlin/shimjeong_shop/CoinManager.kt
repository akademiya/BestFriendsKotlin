package com.vadym.gvd.bestfriendskotlin.shimjeong_shop

import android.content.Context

class CoinManager(context: Context) {

    private val prefs = context.getSharedPreferences("shimjeong_coins", Context.MODE_PRIVATE)

    companion object {
        const val COINS_PER_DAY = 5
        private const val KEY_BALANCE = "balance"
        private const val KEY_PURCHASED = "purchased_cards"
    }

    val balance: Int
        get() = prefs.getInt(KEY_BALANCE, 0)

    fun addCoins(amount: Int) {
        prefs.edit().putInt(KEY_BALANCE, balance + amount).apply()
    }

    fun spendCoins(amount: Int): Boolean {
        if (balance < amount) return false
        prefs.edit().putInt(KEY_BALANCE, balance - amount).apply()
        return true
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
}