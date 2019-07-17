package com.example.smartride.screens.trivia

class TriviaModel {
    data class Question(
        val question: String,
        val answer1: String,
        val answer2: String,
        val answer3: String,
        val answer4: String
    )
}