package com.example.smartride.screens.trivia

class TriviaModel {
    data class Question(
        val questionNumber: Int,
        val questionText: String,
        val userAnswered: Boolean = false,
        val answers :List<Answer> = listOf()
    ) {
        fun reset(){
            answers.forEach {
                it.state = State.IDEL
            }
        }

    }

    data class Answer(
        val answer: String = "",
        val isTheRightOne: Boolean = false,
        var state: State = State.IDEL
    )

    enum class State {
        ERROR, RIGHT, IDEL, DISABLED
    }
}