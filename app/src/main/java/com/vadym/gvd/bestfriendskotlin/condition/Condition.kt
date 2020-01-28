package com.vadym.gvd.bestfriendskotlin.condition

class Condition {
    var conditionId: Int = 0
    var lider: String? = null
    var duration: String? = null
    var today: String? = null
    var condition: String? = null
    var pubGoal: String? = null
    var perGoal: String? = null
    var conditionPosition: Int = 0

    constructor(conditionId: Int,
                lider: String,
                duration: String,
                today: String,
                condition: String,
                pubGoal: String,
                perGoal: String,
                conditionPosition: Int) {
        this.conditionId = conditionId
        this.lider = lider
        this.duration = duration
        this.today = today
        this.condition = condition
        this.pubGoal = pubGoal
        this.perGoal = perGoal
        this.conditionPosition = conditionPosition
    }

    constructor(lider: String,
                duration: String,
                today: String,
                condition: String,
                pubGoal: String,
                perGoal: String,
                conditionPosition: Int) {
        this.lider = lider
        this.duration = duration
        this.today = today
        this.condition = condition
        this.pubGoal = pubGoal
        this.perGoal = perGoal
        this.conditionPosition = conditionPosition
    }

    constructor(conditionId: Int, conditionPosition: Int) {
        this.conditionId = conditionId
        this.conditionPosition = conditionPosition
    }
}

