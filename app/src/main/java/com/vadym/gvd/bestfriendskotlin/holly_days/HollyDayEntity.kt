package com.vadym.gvd.bestfriendskotlin.holly_days

class HollyDayEntity {
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