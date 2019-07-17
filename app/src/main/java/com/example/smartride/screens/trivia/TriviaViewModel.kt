package com.example.smartride.screens.trivia

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TriviaViewModel : ViewModel() {

    data class TriviaState(val notes: List<TriviaModel.Question>? = null)

    val feedData: MutableLiveData<TriviaState> = MutableLiveData()

    init {
        feedData.postValue(TriviaState())
    }

}