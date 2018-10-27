package com.vadym.gvd.bestfriendskotlin

import javax.inject.Singleton

@Singleton
object MainPresenter {
    var isPrivacyPolicy = false

    fun updatePrivacyPolicy(isPrivacyPolicy: Boolean) {
        this.isPrivacyPolicy = isPrivacyPolicy
    }
}