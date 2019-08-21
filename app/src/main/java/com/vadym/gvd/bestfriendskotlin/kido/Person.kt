package com.vadym.gvd.bestfriendskotlin.kido

class Person {
    var personId: Int = 0
    var personName: String? = null
    var personDescription: String? = null
    var personPosition: Int = 0
    var counter: Int = 0

    constructor(personId: Int, personName: String?, personDescription: String?, personPosition: Int) {
        this.personId = personId
        this.personName = personName
        this.personDescription = personDescription
        this.personPosition = personPosition
    }

    constructor(personName: String?, personDescription: String?, personPosition: Int) {
        this.personName = personName
        this.personDescription = personDescription
        this.personPosition = personPosition
    }

    constructor(personPosition: Int) {
        this.personPosition = personPosition
    }

}