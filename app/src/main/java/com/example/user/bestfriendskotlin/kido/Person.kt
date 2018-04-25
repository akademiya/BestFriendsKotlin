package com.example.user.bestfriendskotlin.kido

class Person {

    var personId: Int = 0
    var personName: String? = null

    constructor(personId: Int, personName: String?) {
        this.personId = personId
        this.personName = personName
    }

    constructor(personName: String?) {
        this.personName = personName
    }

}