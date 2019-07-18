package com.example.smartride.screens.trivia

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.smartride.R
import com.example.smartride.base.BaseFragment
import com.example.smartride.utils.changed
import com.example.smartride.widgets.MainToolBar
import kotlinx.android.synthetic.main.fragment_trivia.*
import lib.yamin.easylog.EasyLog

class TriviaFragment : BaseFragment() {

    private lateinit var triviaVM: TriviaViewModel
    override fun toolBarMode() = MainToolBar.ToolBarMode.BACK
    var lastState: TriviaState = TriviaState(
        question = TriviaModel.Question("", listOf()),
        hasHalf = true, hasPlusFive = true)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        triviaVM = ViewModelProviders.of(requireActivity(), TriviaViewModelFactory()).get(TriviaViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_trivia, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        triviaVM.questionData.observe(this, Observer {
            handleQuestion(it)
        })

        triviaHalf.setOnClickListener {
            EasyLog.e("Half")
        }
        triviaPlus.setOnClickListener {
            EasyLog.e("triviaPlus")
        }
        triviaQuestionContainer1.setOnClickListener {
            EasyLog.e("triviaPlus")
            triviaVM.answerClicked(1)
        }
        triviaQuestionContainer2.setOnClickListener {
            triviaVM.answerClicked(2)
        }
        triviaQuestionContainer3.setOnClickListener {
            triviaVM.answerClicked(3)
        }
        triviaQuestionContainer4.setOnClickListener {
            triviaVM.answerClicked(4)
        }

        triviaVM.updateNextQuestion()
    }

    private fun handleQuestion(state: TriviaState) {
        EasyLog.e(state)
        state.changed(lastState, { hasHalf }, action = {
                triviaHalf.isEnabled = it
        })
        state.changed(lastState, { hasPlusFive }, action = {
                triviaPlus.isEnabled = it
        })
        state.changed(lastState, { question }, action = {
            triviaQuestion.text = it.question
            triviaQuestion1.text = it.answers[1].answer
            triviaQuestion2.text = it.answers[2].answer
            triviaQuestion3.text = it.answers[3].answer
            triviaQuestion4.text = it.answers[4].answer
        })
        lastState = state
    }

    fun bindAnswer(container:ConstraintLayout, text:TextView, answer: TriviaModel.Answer) {
        text.text = answer.answer
        when(answer.state){
            TriviaModel.State.ERROR -> TODO()
            TriviaModel.State.RIGHT -> TODO()
            TriviaModel.State.IDEL -> TODO()
            TriviaModel.State.DISABLED -> TODO()
        }
    }
}