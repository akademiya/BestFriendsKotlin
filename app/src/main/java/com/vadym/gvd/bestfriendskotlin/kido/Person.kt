package com.vadym.gvd.bestfriendskotlin.kido

class Person {
    var personId: Int = 0
    var personName: String? = null
    var personDescription: String? = null
    var counter: Int = 0

    constructor(personId: Int, personName: String?, personDescription: String?) {
        this.personId = personId
        this.personName = personName
        this.personDescription = personDescription
    }

    constructor(personName: String?, personDescription: String?) {
        this.personName = personName
        this.personDescription = personDescription
    }

}