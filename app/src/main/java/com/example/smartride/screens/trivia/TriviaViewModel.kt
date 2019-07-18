package com.example.smartride.screens.trivia

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import lib.yamin.easylog.EasyLog
import java.lang.Exception

class TriviaViewModel : ViewModel(), ValueEventListener {

    override fun onCancelled(p0: DatabaseError) {

    }

    override fun onDataChange(dataSnapshot: DataSnapshot) {
        notes.clear()
        try {
            dataSnapshot.children.forEachIndexed { index, dataSnapshot ->
//                EasyLog.d("Ques: $it")
                val question = dataSnapshot.child("q").value as String

                val answers = mutableListOf<TriviaModel.Answer>()

                dataSnapshot.child("answers").children.forEachIndexed { index, dataSnapshot ->
                    val answer = dataSnapshot.child("a").value as String
                    val isCorrect = dataSnapshot.child("isCorrect").value as Boolean

                    answers.add(TriviaModel.Answer(answer, isCorrect))
                }

                notes.add(TriviaModel.Question(question, answers))
            }

            EasyLog.d("Ques Final List: $notes")

        } catch (e: Exception) {

        }
    }

    var currentQuestion: Int = 0
    val notes: MutableList<TriviaModel.Question> = mutableListOf()

    val questionData: MutableLiveData<TriviaState> = MutableLiveData()

    private var databaseReference: DatabaseReference? = null

    init {
        databaseReference = FirebaseDatabase.getInstance().getReference("questions")
        databaseReference?.addValueEventListener(this)
    }

    override fun onCleared() {
        super.onCleared()

        databaseReference?.removeEventListener(this)
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
