package com.example.smartride.screens.trivia

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
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
    var lastState: TriviaState = TriviaState(
        question = TriviaModel.Question(-1, "", false, listOf()),
        hasHalf = true, hasPlusFive = true)

    override fun toolBarMode() = MainToolBar.ToolBarMode.BACK

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
            triviaVM.onHalfClicked()
        }
        triviaPlus.setOnClickListener {
            var curProgress = triviaLottieTimer.progress
            EasyLog.e("curProgress: $curProgress")
            curProgress = if (curProgress < 0.33){
                0f
            } else {
                curProgress - 0.33f
            }
            EasyLog.e("updatedProgress: $curProgress")
            triviaLottieTimer.progress = curProgress
            triviaVM.onPlusFiveClicked()
        }
        triviaQuestionContainer1.setOnClickListener {
            triviaVM.answerClicked(0)
        }
        triviaQuestionContainer2.setOnClickListener {
            triviaVM.answerClicked(1)
        }
        triviaQuestionContainer3.setOnClickListener {
            triviaVM.answerClicked(2)
        }
        triviaQuestionContainer4.setOnClickListener {
            triviaVM.answerClicked(3)
        }

        triviaLottieTimer.imageAssetsFolder = "assets/";
        triviaLottieTimer.setAnimation("timer_animation.json")
        triviaLottieTimer.addAnimatorUpdateListener {
            if (it.animatedFraction >= 1) {
                triviaVM.timerEnded()
            }
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
        state.question.changed(lastState.question, { questionText }, action = {
            triviaQuestion.text = it
            startNewTimer()
        })
        state.changed(lastState, { finishedStage }, action = {
            if (it) {
                EasyLog.e("finishedStage")
                requireActivity().onBackPressed()
            }
        })
        state.question.changed(lastState.question, { questionNumber }, action = {
            triviaQuestionNumber.text = "Q$it"
        })

        bindAnswer(triviaQuestionContainer1, triviaQuestion1, triviaIcon1, state.question.answers[0])
        bindAnswer(triviaQuestionContainer2, triviaQuestion2, triviaIcon2, state.question.answers[1])
        bindAnswer(triviaQuestionContainer3, triviaQuestion3, triviaIcon3, state.question.answers[2])
        bindAnswer(triviaQuestionContainer4, triviaQuestion4, triviaIcon4, state.question.answers[3])

        state.question.changed(lastState.question, { userAnswered }, action = {
            if (it) {
                triviaQuestionContainer1.isEnabled = false
                triviaQuestionContainer2.isEnabled = false
                triviaQuestionContainer3.isEnabled = false
                triviaQuestionContainer4.isEnabled = false
                triviaLottieTimer.cancelAnimation()
            }
        })
        lastState = state
    }

    private fun startNewTimer() {
        triviaLottieTimer.playAnimation()
    }

    private fun bindAnswer(
        container:ConstraintLayout,
        text:TextView,
        icon:ImageView,
        answer: TriviaModel.Answer
    ) {
        text.text = answer.answer
        when(answer.state){
            TriviaModel.State.ERROR -> {
                text.setTextColor(ContextCompat.getColor(text.context, R.color._white))
                icon.background = ContextCompat.getDrawable(container.context, R.drawable.ic_trivia_error)
                container.setBackgroundResource(R.drawable.rounded_red)
                container.isEnabled = false
            }
            TriviaModel.State.RIGHT -> {
                text.setTextColor(ContextCompat.getColor(text.context, R.color._white))
                icon.background = ContextCompat.getDrawable(container.context, R.drawable.ic_trivia_v)
                container.setBackgroundResource(R.drawable.rounded_green)
                container.isEnabled = false
            }
            TriviaModel.State.IDEL -> {
                text.setTextColor(ContextCompat.getColor(text.context, R.color.colorAccent))
                icon.background = null
                container.setBackgroundResource(R.drawable.rounded_white)
                container.isEnabled = true
            }
            TriviaModel.State.DISABLED -> {
                text.setTextColor(ContextCompat.getColor(text.context, R.color.blue_grey_100))
                icon.background = null
                container.setBackgroundResource(R.drawable.rounded_white)
                container.isEnabled = false
            }
        }
    }
}