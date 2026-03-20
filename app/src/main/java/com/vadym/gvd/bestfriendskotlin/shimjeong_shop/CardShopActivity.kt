package com.vadym.gvd.bestfriendskotlin.shimjeong_shop

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.vadym.gvd.bestfriendskotlin.BaseActivity
import com.vadym.gvd.bestfriendskotlin.MainActivity
import com.vadym.gvd.bestfriendskotlin.MainActivity.Companion.EXTRA_OPEN_DRAWER
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.databinding.ActivityCardShopBinding

class CardShopActivity : MainActivity() {

    private lateinit var binding: ActivityCardShopBinding
    private lateinit var coinManager: CoinManager
    private lateinit var adapter: CardShopAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardShopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        coinManager = CoinManager(this)

        setupToolbar()
        setupRecyclerView()
        updateBalanceDisplay()

        findViewById<LinearLayout>(R.id.coin_balance_toolbar).setOnClickListener {
            showBalanceDialog(coinManager.balance)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun setupRecyclerView() {
        adapter = CardShopAdapter(ShopCard.all, coinManager) { card ->
            showPurchaseDialog(card)
        }
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(this@CardShopActivity, 2)
            adapter = this@CardShopActivity.adapter
            addItemDecoration(GridSpacingItemDecoration(2, 16.dp, true))
        }
    }

    private fun showPurchaseDialog(card: ShopCard) {
        if (coinManager.balance < card.price) {
            MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.not_enough_coins))
                .setMessage(getString(R.string.need_coins_format, card.price))
                .setPositiveButton(android.R.string.ok, null)
                .show()
            return
        }

        MaterialAlertDialogBuilder(this)
            .setMessage(getString(R.string.confirm_purchase_format, card.price))
            .setPositiveButton(R.string.buy) { _, _ ->
                if (coinManager.purchaseCard(card.id)) {
                    adapter.refreshCard(card.id)
                    updateBalanceDisplay()
                    showPurchaseSuccess()
                }
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    private fun showPurchaseSuccess() {
        val mediaPlayer = MediaPlayer.create(this, R.raw.sj_coin)
        mediaPlayer.setOnCompletionListener { it.release() }
        mediaPlayer.start()
        Snackbar.make(binding.root, R.string.card_unlocked, Snackbar.LENGTH_SHORT).show()
    }

    private fun updateBalanceDisplay() {
        binding.coinBalance.text = "${coinManager.balance} SC"
    }

    private fun showBalanceDialog(balance: Int) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_balance, null)
        dialogView.findViewById<TextView>(R.id.balance_text).text = "${balance} 심정 Coins"

        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.coins_balance_title))
            .setView(dialogView)
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }

    private val Int.dp get() = (this * resources.displayMetrics.density).toInt()

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra(EXTRA_OPEN_DRAWER, true)
        }
        startActivity(intent)
        finish()
    }
}