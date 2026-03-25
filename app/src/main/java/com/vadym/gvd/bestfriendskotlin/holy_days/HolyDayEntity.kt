package com.vadym.gvd.bestfriendskotlin.holy_days

import com.google.firebase.database.PropertyName

data class HolyDayEntity(
    @get:PropertyName("id")    @set:PropertyName("id")    var id: String?    = null,
    @get:PropertyName("title") @set:PropertyName("title") var title: String? = null,
    @get:PropertyName("day")   @set:PropertyName("day")   var day: String?   = null
)