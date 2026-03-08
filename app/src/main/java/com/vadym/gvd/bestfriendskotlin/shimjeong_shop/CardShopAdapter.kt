package com.vadym.gvd.bestfriendskotlin.shimjeong_shop

import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.vadym.gvd.bestfriendskotlin.R
import com.vadym.gvd.bestfriendskotlin.databinding.ItemShopCardBinding
import jp.wasabeef.glide.transformations.BlurTransformation

class CardShopAdapter(
    private val cards: List<ShopCard>,
    private val coinManager: CoinManager,
    private val onPurchaseClick: (ShopCard) -> Unit
) : RecyclerView.Adapter<CardShopAdapter.CardViewHolder>() {

    inner class CardViewHolder(val binding: ItemShopCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = ItemShopCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cards[position]
        val purchased = coinManager.isCardPurchased(card.id)

        with(holder.binding) {
            if (purchased) {
                cardImage.setImageResource(card.imageRes)
                cardImage.visibility = View.VISIBLE
                purchasedBadge.visibility = View.VISIBLE
                cardBlob.visibility = View.GONE
                lockIcon.visibility = View.GONE
                priceGroup.visibility = View.GONE
                expandIcon.visibility = View.VISIBLE

                val openFull = View.OnClickListener {
                    showFullscreenDialog(root.context, card.imageRes)
                }
//                root.setOnClickListener(openFull)
                expandIcon.setOnClickListener(openFull)
            } else {
                cardImage.visibility = View.GONE
                expandIcon.visibility = View.GONE
                purchasedBadge.visibility = View.GONE
                cardBlob.visibility = View.VISIBLE
                lockIcon.visibility = View.VISIBLE
                priceGroup.visibility = View.VISIBLE
                priceText.text = "${card.price}"

                Glide.with(root.context)
                    .load(card.imageRes)
                    .apply(
                        RequestOptions()
                            .transform(BlurTransformation(25, 3), CircleCrop())
                            .override(300, 300)
                    )
                    .into(cardBlob)

                root.setOnClickListener { onPurchaseClick(card) }
            }
        }
    }

    override fun getItemCount() = cards.size

    fun refreshCard(cardId: Int) {
        val idx = cards.indexOfFirst { it.id == cardId }
        if (idx != -1) notifyItemChanged(idx)
    }

    private fun showFullscreenDialog(context: Context, imageRes: Int) {
        val dialogView = LayoutInflater.from(context)
            .inflate(R.layout.dialog_card_fullscreen, null)

        val fullImage = dialogView.findViewById<ImageView>(R.id.full_image)
        val btnClose = dialogView.findViewById<ImageView>(R.id.btn_close)

        val dialog = AlertDialog.Builder(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
            .setView(dialogView)
            .create()

        dialog.window?.apply {
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            setBackgroundDrawableResource(android.R.color.white)
            fullImage.setImageResource(imageRes)
        }

        btnClose.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
}