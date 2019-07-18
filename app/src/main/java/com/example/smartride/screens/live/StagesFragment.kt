package com.example.smartride.screens.live

import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.example.smartride.R
import com.example.smartride.base.BaseFragment
import com.example.smartride.screens.trivia.RideState
import com.example.smartride.screens.trivia.TriviaViewModel
import com.example.smartride.screens.trivia.TriviaViewModelFactory
import com.example.smartride.utils.changed
import com.example.smartride.widgets.MainToolBar
import kotlinx.android.synthetic.main.fragment_stages.*
import lib.yamin.easylog.EasyLog

class StagesFragment : BaseFragment() {

    private lateinit var triviaVM: TriviaViewModel
    var lastState = RideState(-1, -1, 0)
    private var animator: ValueAnimator? = null

    override fun toolBarMode() = MainToolBar.ToolBarMode.BACK

    override fun onAttach(context: Context) {
        super.onAttach(context)
        triviaVM = ViewModelProviders.of(requireActivity(), TriviaViewModelFactory()).get(TriviaViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProviders.of(requireActivity(), TriviaViewModelFactory()).get(TriviaViewModel::class.java)
        viewModel.rideData.observe(this, Observer {

            EasyLog.d("Current Stage: ${it.currentStage}")


        })
    }

    private var stage: Int = -1
    private fun update() {
        when (/*it.currentStage*/stage) {
            0 -> setSnakeAnimation(0f, 0.01f)
            1 -> setSnakeAnimation(0f, 0.06f)
            2 -> setSnakeAnimation(0.06f, 0.1f)
            3 -> setSnakeAnimation(0.1f, 0.13f)
            4 -> setSnakeAnimation(0.13f, 0.15f)
            5 -> setSnakeAnimation(0.15f, 0.22f)
            6 -> setSnakeAnimation(0.22f, 0.26f)
            7 -> setSnakeAnimation(0.30f, 0.34f)
            8 -> setSnakeAnimation(0.34f, 0.38f)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stagesLottieStages.setOnClickListener {
            stage++
            update()
//            EasyLog.e()
//
//            val navController = NavHostFragment.findNavController(this)
//            navController.navigate(R.id.action_stagesFragment_to_gamePreviewFragment)
        }
        triviaVM.rideData.observe(this, Observer { state ->
            handleState(state)
        })
    }

    private fun handleState(state: RideState) {
        state.changed(lastState, { distanceToDestination }, action = {
            stagesDistanceLeft.animateSetText(it.toString())
        })
    }

    private fun setSnakeAnimation(from: Float, to: Float) {

        animator?.cancel()
        animator?.removeAllUpdateListeners()

        animator = ValueAnimator.ofFloat(from, to)
        animator?.duration = 1000
        animator?.addUpdateListener {
            stagesLottieStages?.progress = it.animatedValue as Float
        }
        animator?.start()
    }

    override fun onDestroy() {
        super.onDestroy()

        animator?.cancel()
        animator?.removeAllUpdateListeners()
    }
}