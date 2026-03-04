package com.vadym.gvd.bestfriendskotlin.holy_days

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class HolyDayItem(
    val entity: HolyDayEntity,
    val index: Int,
    @DrawableRes val imageRes: Int? = null,
    @StringRes val titleRes: Int,
    @StringRes val descriptionRes: Int,
    val isMajor: Boolean = false
)
