package com.example.smartride.screens.trivia

class TriviaModel {
    data class Question(
        val question: String,
        val answers :List<Answer> = listOf()
    )

    data class Answer(
        val answer: String = "",
        val isTheRightOne: Boolean = false,
        var state: State = State.IDEL
    )

    enum class State {
        ERROR, RIGHT, IDEL, DISABLED
    }
}