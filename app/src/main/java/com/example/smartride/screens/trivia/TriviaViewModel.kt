package com.example.smartride.screens.trivia

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import lib.yamin.easylog.EasyLog

class TriviaViewModel : ViewModel(), ValueEventListener {

    var currentStage = 1
    private var currentQuestion: Int = 0
    private val questions: MutableList<TriviaModel.Question> = mutableListOf()

    val questionData: MutableLiveData<TriviaState> = MutableLiveData()
    val rideData: MutableLiveData<RideState> = MutableLiveData()

    private var databaseReference: DatabaseReference? = null

    init {
        databaseReference = FirebaseDatabase.getInstance().getReference("questions")
        databaseReference?.addValueEventListener(this)
        rideData.postValue(RideState())
    }

    fun updateNextQuestion() {
        if (questions.isEmpty()) {
            return
        }

        val nextQuestion = questions[currentQuestion]
        val newState = questionData.value?.copy(question = nextQuestion) ?: TriviaState(nextQuestion)
        questionData.postValue(newState)
    }

    fun answerClicked(position: Int) {
        val curQuestion = questions[currentQuestion].copy(userAnswered = true)
        if (curQuestion.answers[position].isTheRightOne) {
            curQuestion.answers[position].state = TriviaModel.State.RIGHT
            onUserAnsweredCorrectly()
        } else {
            curQuestion.answers[position].state = TriviaModel.State.ERROR
        }
        val newState = questionData.value?.copy(question = curQuestion) ?: TriviaState(curQuestion)
        questionData.postValue(newState)
        triggerNextQuestion()
    }

    fun timerEnded() {
        val userAnswered = questionData.value!!.question.copy(userAnswered = true)
        val newState = questionData.value!!.copy(question = userAnswered)
        questionData.postValue(newState)
        triggerNextQuestion()
    }

    fun onPlusFiveClicked() {
        val newState = questionData.value!!.copy(hasPlusFive = false)
        questionData.postValue(newState)
    }

    fun onHalfClicked() {
        val halfAnswers = questionData.value!!.question
        var count = 0
        halfAnswers.answers.forEach {
            if (count < 2 && !it.isTheRightOne) {
                it.state = TriviaModel.State.DISABLED
                count++
            }
        }
        val newState = questionData.value!!.copy(question = halfAnswers, hasHalf = false)
        questionData.postValue(newState)
    }

    private fun triggerNextQuestion() {
        Handler().postDelayed({
            val curQuestion = questions[currentQuestion]
            curQuestion.reset()

            currentQuestion = (currentQuestion + 1) % questions.size

            EasyLog.e("currentStage: $currentStage:: ${currentStage % 3 == 0}")
            if (currentQuestion % 3 == 0) {
                currentStage++
                sendFinishedStage()
            } else {
                updateNextQuestion()
            }
        }, 2000)
    }

    private fun sendFinishedStage() {
        val currentStage = rideData.value!!.currentStage + 1
        rideData.postValue(rideData.value!!.copy(currentStage = currentStage))

        val newState = questionData.value!!.copy(finishedStage = true)
        questionData.postValue(newState)
    }

    fun onRideStarted() {
        val currentDistance = rideData.value!!.distanceToDestination
        rideData.postValue(rideData.value!!.copy(currentStage = currentStage))
    }

    override fun onDataChange(dataSnapshot: DataSnapshot) {
        questions.clear()
        try {
            dataSnapshot.children.forEachIndexed { index, dataSnapshot ->
                //                EasyLog.d("Ques: $it")
                val question = dataSnapshot.child("q").value as String
                val answers = mutableListOf<TriviaModel.Answer>()

                dataSnapshot.child("answers").children.forEachIndexed { _, dataSnapshot ->
                    val answer = dataSnapshot.child("a").value as String
                    val isCorrect = dataSnapshot.child("isCorrect").value as? Boolean ?: false

                    answers.add(TriviaModel.Answer(answer, isCorrect))
                }
                EasyLog.e("answers: ${answers.toString()}")
                questions.add(TriviaModel.Question((index + 1), question, false, answers))
            }
            EasyLog.d("Ques Final List: $questions")
        } catch (e: Exception) {
            EasyLog.e(e)
        }
    }

    override fun onCancelled(p0: DatabaseError) {

    }

    override fun onCleared() {
        super.onCleared()

        databaseReference?.removeEventListener(this)
    }

    private fun onUserAnsweredCorrectly() {
        EasyLog.e()
    }

}

data class TriviaState(
    val question: TriviaModel.Question,
    val finishedStage: Boolean = false,
    val hasPlusFive: Boolean = true,
    val hasHalf: Boolean = true
)

data class RideState(
    val currentStage: Int = 1,
    val rideDistance: Int = 50,
    val distanceToDestination: Int = 22
)


