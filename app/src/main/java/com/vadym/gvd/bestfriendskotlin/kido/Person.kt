package com.vadym.gvd.bestfriendskotlin.kido

import android.graphics.Bitmap

class Person {
    var personId: Int = 0
    var personName: String? = null
    var personDescription: String? = null
    var personPosition: Int = 0
    var counter: Int = 0
    var personPhoto: Bitmap? = null
    var personPhotoByte: ByteArray? = null

    constructor(personId: Int, personName: String?, personDescription: String?, personPhoto: Bitmap?, personPosition: Int) {
        this.personId = personId
        this.personName = personName
        this.personDescription = personDescription
        this.personPhoto = personPhoto
        this.personPosition = personPosition
    }

    constructor(personName: String?, personDescription: String?, personPhotoByte: ByteArray?, personPosition: Int) {
        this.personName = personName
        this.personDescription = personDescription
        this.personPhotoByte = personPhotoByte
        this.personPosition = personPosition
    }

    constructor(personPosition: Int) {
        this.personPosition = personPosition
    }

}