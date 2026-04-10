package com.vadym.gvd.bestfriendskotlin.easter_egg

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coins")
data class CoinEntity(
    @PrimaryKey val id: String,
    val activityTag: String,
    val viewResId: Int,
    val isCollected: Boolean = false,
    val isVisibleToday: Boolean = false,
    val lastShownDate: String = ""
)