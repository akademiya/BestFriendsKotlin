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
                ShopCard(1,  R.drawable.tp_card_shop_01, 10,  R.color.blob_15),
                ShopCard(2,  R.drawable.tp_card_shop_02, 10,  R.color.blob_15),
                ShopCard(3,  R.drawable.tp_card_shop_03, 10,  R.color.blob_15),
                ShopCard(4,  R.drawable.tp_card_shop_04, 10,  R.color.blob_15),
                ShopCard(5,  R.drawable.tp_card_shop_05, 10,  R.color.blob_15),
                ShopCard(6,  R.drawable.tp_card_shop_06, 10,  R.color.blob_15),
                ShopCard(7,  R.drawable.tp_card_shop_07, 10,  R.color.blob_15),

                // 15 SC — 7 карточок
                ShopCard(8,  R.drawable.tp_card_shop_08, 15,  R.color.blob_15),
                ShopCard(9,  R.drawable.tp_card_shop_09, 15,  R.color.blob_15),
                ShopCard(10, R.drawable.tp_card_shop_10, 15,  R.color.blob_15),
                ShopCard(11, R.drawable.tp_card_shop_11, 15,  R.color.blob_15),
                ShopCard(12, R.drawable.tp_card_shop_12, 15,  R.color.blob_15),
                ShopCard(13, R.drawable.tp_card_shop_13, 15,  R.color.blob_15),
                ShopCard(14, R.drawable.tp_card_shop_14, 15,  R.color.blob_15),

                // 25 SC — 10 карточок
                ShopCard(15, R.drawable.tp_card_shop_15, 20,  R.color.blob_25),
                ShopCard(16, R.drawable.tp_card_shop_16, 20,  R.color.blob_25),
                ShopCard(17, R.drawable.tp_card_shop_17, 20,  R.color.blob_25),
                ShopCard(18, R.drawable.tp_card_shop_18, 20,  R.color.blob_25),
                ShopCard(19, R.drawable.tp_card_shop_19, 20,  R.color.blob_25),
                ShopCard(20, R.drawable.tp_card_shop_20, 20,  R.color.blob_25),
                ShopCard(21, R.drawable.tp_card_shop_21, 20,  R.color.blob_25),
                ShopCard(22, R.drawable.tp_card_shop_22, 20,  R.color.blob_25),
                ShopCard(23, R.drawable.tp_card_shop_23, 20,  R.color.blob_25),
                ShopCard(24, R.drawable.tp_card_shop_24, 20,  R.color.blob_25),

                // 35 SC — 7 карточок
                ShopCard(25, R.drawable.tp_card_shop_25, 30,  R.color.blob_35),
                ShopCard(26, R.drawable.tp_card_shop_26, 30,  R.color.blob_35),
                ShopCard(27, R.drawable.tp_card_shop_27, 30,  R.color.blob_35),
                ShopCard(28, R.drawable.tp_card_shop_28, 30,  R.color.blob_35),
                ShopCard(29, R.drawable.tp_card_shop_29, 30,  R.color.blob_35),
                ShopCard(30, R.drawable.tp_card_shop_30, 30,  R.color.blob_35),
                ShopCard(31, R.drawable.tp_card_shop_31, 30,  R.color.blob_35),

                // 50 SC — 5 карточок
                ShopCard(32, R.drawable.tp_card_shop_32, 50,  R.color.blob_50),
                ShopCard(33, R.drawable.tp_card_shop_33, 50,  R.color.blob_50),
                ShopCard(34, R.drawable.tp_card_shop_34, 50,  R.color.blob_50),
                ShopCard(35, R.drawable.tp_card_shop_35, 50,  R.color.blob_50),
                ShopCard(36, R.drawable.tp_card_shop_36, 50,  R.color.blob_50),

                // 75 SC — 3 карточки
                ShopCard(37, R.drawable.tp_card_shop_37, 75,  R.color.blob_75),
                ShopCard(38, R.drawable.tp_card_shop_38, 75,  R.color.blob_75),
                ShopCard(39, R.drawable.tp_card_shop_39, 75,  R.color.blob_75),

                // 100 SC — 1 карточка
                ShopCard(40, R.drawable.tp_card_shop_40, 100,  R.color.blob_100)
            )
        }
    }
}
