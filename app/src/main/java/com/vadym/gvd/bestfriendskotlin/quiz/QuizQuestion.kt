package com.vadym.gvd.bestfriendskotlin.quiz

data class QuizQuestion(
    val text: String,
    val answers: List<String>,
    val correctIndex: Int
)