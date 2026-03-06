package com.vadym.gvd.bestfriendskotlin.shimjeong_shop

import androidx.annotation.ColorRes
import com.vadym.gvd.bestfriendskotlin.R

data class ShopCard(
    val id: Int,
    val imageRes: Int,
    val price: Int,
    @ColorRes val blobColor: Int
) {
    companion object {
        val all: List<ShopCard> by lazy {
            listOf(
                // 15 SC — 7 карточок
                ShopCard(1,  R.drawable.tp_card_shop_01, 15,  R.color.blob_15),
                ShopCard(2,  R.drawable.tp_card_shop_02, 15,  R.color.blob_15),
                ShopCard(3,  R.drawable.tp_card_shop_03, 15,  R.color.blob_15),
                ShopCard(4,  R.drawable.tp_card_shop_04, 15,  R.color.blob_15),
                ShopCard(5,  R.drawable.tp_card_shop_05, 15,  R.color.blob_15),
                ShopCard(6,  R.drawable.tp_card_shop_06, 15,  R.color.blob_15),
                ShopCard(7,  R.drawable.tp_card_shop_07, 15,  R.color.blob_15),

                // 25 SC — 5 карточок
                ShopCard(8,  R.drawable.tp_card_shop_08, 25,  R.color.blob_25),
                ShopCard(9,  R.drawable.tp_card_shop_09, 25,  R.color.blob_25),
                ShopCard(10, R.drawable.tp_card_shop_10, 25,  R.color.blob_25),
                ShopCard(11, R.drawable.tp_card_shop_11, 25,  R.color.blob_25),
                ShopCard(12, R.drawable.tp_card_shop_01, 25,  R.color.blob_25),

                // 35 SC — 4 карточки
                ShopCard(13, R.drawable.tp_card_shop_01, 35,  R.color.blob_35),
                ShopCard(14, R.drawable.tp_card_shop_01, 35,  R.color.blob_35),
                ShopCard(15, R.drawable.tp_card_shop_01, 35,  R.color.blob_35),
                ShopCard(16, R.drawable.tp_card_shop_01, 35,  R.color.blob_35),

                // 50 SC — 3 карточки
                ShopCard(17, R.drawable.tp_card_shop_01, 50,  R.color.blob_50),
                ShopCard(18, R.drawable.tp_card_shop_01, 50,  R.color.blob_50),
                ShopCard(19, R.drawable.tp_card_shop_01, 50,  R.color.blob_50),

                // 75 SC — 2 карточки
                ShopCard(20, R.drawable.tp_card_shop_01, 75,  R.color.blob_75),
                ShopCard(21, R.drawable.tp_card_shop_01, 75,  R.color.blob_75),

                // 100 SC — 1 карточка
                ShopCard(22, R.drawable.tp_card_shop_01, 100,  R.color.blob_100)
            )
        }
    }
}
