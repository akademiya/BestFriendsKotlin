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
                // 10 SC — 7 карточок
                ShopCard(1,  R.drawable.tp_card_shop_01, 15,  R.color.blob_15),
                ShopCard(2,  R.drawable.tp_card_shop_02, 15,  R.color.blob_15),
                ShopCard(3,  R.drawable.tp_card_shop_03, 15,  R.color.blob_15),
                ShopCard(4,  R.drawable.tp_card_shop_04, 15,  R.color.blob_15),
                ShopCard(5,  R.drawable.tp_card_shop_05, 15,  R.color.blob_15),
                ShopCard(6,  R.drawable.tp_card_shop_06, 15,  R.color.blob_15),
                ShopCard(7,  R.drawable.tp_card_shop_07, 15,  R.color.blob_15),

                // 15 SC — 7 карточок
                ShopCard(8,  R.drawable.tp_card_shop_08, 25,  R.color.blob_15),
                ShopCard(9,  R.drawable.tp_card_shop_09, 25,  R.color.blob_15),
                ShopCard(10, R.drawable.tp_card_shop_10, 25,  R.color.blob_15),
                ShopCard(11, R.drawable.tp_card_shop_11, 25,  R.color.blob_15),
                ShopCard(12, R.drawable.tp_card_shop_12, 25,  R.color.blob_15),
                ShopCard(13, R.drawable.tp_card_shop_13, 25,  R.color.blob_15),
                ShopCard(14, R.drawable.tp_card_shop_14, 25,  R.color.blob_15),

                // 25 SC — 10 карточок
                ShopCard(15, R.drawable.tp_card_shop_15, 40,  R.color.blob_25),
                ShopCard(16, R.drawable.tp_card_shop_16, 40,  R.color.blob_25),
                ShopCard(17, R.drawable.tp_card_shop_17, 40,  R.color.blob_25),
                ShopCard(18, R.drawable.tp_card_shop_18, 40,  R.color.blob_25),
                ShopCard(19, R.drawable.tp_card_shop_19, 40,  R.color.blob_25),
                ShopCard(20, R.drawable.tp_card_shop_20, 40,  R.color.blob_25),
                ShopCard(21, R.drawable.tp_card_shop_21, 40,  R.color.blob_25),
                ShopCard(22, R.drawable.tp_card_shop_22, 40,  R.color.blob_25),
                ShopCard(23, R.drawable.tp_card_shop_23, 40,  R.color.blob_25),
                ShopCard(24, R.drawable.tp_card_shop_24, 40,  R.color.blob_25),

                // 35 SC — 7 карточок
                ShopCard(25, R.drawable.tp_card_shop_25, 70,  R.color.blob_35),
                ShopCard(26, R.drawable.tp_card_shop_26, 70,  R.color.blob_35),
                ShopCard(27, R.drawable.tp_card_shop_27, 70,  R.color.blob_35),
                ShopCard(28, R.drawable.tp_card_shop_28, 70,  R.color.blob_35),
                ShopCard(29, R.drawable.tp_card_shop_29, 70,  R.color.blob_35),
                ShopCard(30, R.drawable.tp_card_shop_30, 70,  R.color.blob_35),
                ShopCard(31, R.drawable.tp_card_shop_31, 70,  R.color.blob_35),

                // 50 SC — 5 карточок
                ShopCard(32, R.drawable.tp_card_shop_32, 90,  R.color.blob_50),
                ShopCard(33, R.drawable.tp_card_shop_33, 90,  R.color.blob_50),
                ShopCard(34, R.drawable.tp_card_shop_34, 90,  R.color.blob_50),
                ShopCard(35, R.drawable.tp_card_shop_35, 90,  R.color.blob_50),
                ShopCard(36, R.drawable.tp_card_shop_36, 90,  R.color.blob_50),

                // 75 SC — 3 карточки
                ShopCard(37, R.drawable.tp_card_shop_37, 120,  R.color.blob_75),
                ShopCard(38, R.drawable.tp_card_shop_38, 120,  R.color.blob_75),
                ShopCard(39, R.drawable.tp_card_shop_39, 120,  R.color.blob_75),

                // 100 SC — 1 карточка
                ShopCard(40, R.drawable.tp_card_shop_40, 210,  R.color.blob_100)
            )
        }
    }
}
