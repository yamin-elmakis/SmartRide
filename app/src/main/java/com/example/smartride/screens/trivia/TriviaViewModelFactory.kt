package com.example.smartride.screens.trivia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TriviaViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass != TriviaViewModel::class.java) {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
        return TriviaViewModel(
        ) as T
    }
}