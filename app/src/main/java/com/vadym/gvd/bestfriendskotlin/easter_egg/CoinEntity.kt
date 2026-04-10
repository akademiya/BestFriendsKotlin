package com.vadym.gvd.bestfriendskotlin.easter_egg

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coins")
data class CoinEntity(
    @PrimaryKey val id: String,          // "coin_home_001"
    val activityTag: String,             // "TreeProfileView"
    val viewResId: Int,                  // R.id.coin_tree_001
    val isCollected: Boolean = false,
    val isVisibleToday: Boolean = false,
    val lastShownDate: String = ""
)