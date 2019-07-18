package com.example.smartride.screens.trivia

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TriviaViewModel : ViewModel() {

    var currentQuestion: Int = 0
    val notes: MutableList<TriviaModel.Question> = mutableListOf()

    val questionData: MutableLiveData<TriviaState> = MutableLiveData()

    init {
        notes.add(TriviaModel.Question("why", listOf(
            TriviaModel.Answer("1"),
            TriviaModel.Answer("2"),
            TriviaModel.Answer("3", isTheRightOne = true),
            TriviaModel.Answer("4"))))
        notes.add(TriviaModel.Question("how", listOf(
            TriviaModel.Answer("11"),
            TriviaModel.Answer("22", isTheRightOne = true),
            TriviaModel.Answer("33", isTheRightOne = false),
            TriviaModel.Answer("44"))))
    }

    fun updateNextQuestion() {
        if (notes.isEmpty()) {
            return
        }
        currentQuestion = (currentQuestion + 1) % notes.size

        val curQuestion = notes[currentQuestion]
        val newState = questionData.value?.copy(question = curQuestion) ?: TriviaState(curQuestion)
        questionData.postValue(newState.copy(hasHalf = false))
    }

    fun answerClicked(position: Int) {
        val curQuestion = notes[currentQuestion]
        if (curQuestion.answers[position].isTheRightOne) {
            curQuestion.answers[position].state = TriviaModel.State.RIGHT
        } else {
            curQuestion.answers[position].state = TriviaModel.State.ERROR
        }
        val newState = questionData.value?.copy(question = curQuestion) ?: TriviaState(curQuestion)
        questionData.postValue(newState.copy(hasHalf = false))
    }
}

data class TriviaState(
    val question: TriviaModel.Question,
    val hasPlusFive: Boolean = true,
    val hasHalf: Boolean = true
)
