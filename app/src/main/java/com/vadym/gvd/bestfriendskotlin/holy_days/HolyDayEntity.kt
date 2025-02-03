package com.vadym.gvd.bestfriendskotlin.holy_days

class HolyDayEntity {
    var id: String? = null
    var title: String? = null
    var day: String? = null

    constructor()

    constructor(id: String, title: String, day: String) {
        this.id = id
        this.title = title
        this.day = day
    }
}