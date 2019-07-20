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
import kotlinx.android.synthetic.main.fragment_live_ride.*

class LiveRideFragment : BaseFragment() {

    private lateinit var triviaVM: TriviaViewModel
    var lastState = RideState(-1, -1, 0)
    private var animator: ValueAnimator? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_live_ride, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        triviaVM = ViewModelProviders.of(requireActivity(), TriviaViewModelFactory()).get(TriviaViewModel::class.java)
        triviaVM.onRideStarted()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        liveSingleCta.setOnClickListener {
            val navController = NavHostFragment.findNavController(this)
            navController.navigate(R.id.action_liveRideFragment_to_stagesFragment)
        }

        liveLottieRoute.imageAssetsFolder = "assets/";
        liveLottieRoute.setAnimation("on_the_ride.json")

        triviaVM.rideData.observe(this, Observer { state ->
            handleState(state)
        })
    }

    private fun handleState(state: RideState) {
        if (lastState.rideDistance < 0) {
            liveLottieRoute?.progress = 1 - (state.distanceToDestination.toFloat() / state.rideDistance.toFloat())
        } else state.changed(lastState, { distanceToDestination }, action = {
            setDistanceLeft(it)

            setDistanceAnimation(
                from = 1 - (lastState.distanceToDestination.toFloat() / state.rideDistance.toFloat()),
                to = 1 - (state.distanceToDestination.toFloat() / state.rideDistance.toFloat())
            )
        })

        lastState = state
    }

    private fun setDistanceLeft(distanceLeft: Int) {
        liveDistanceLeft.setText(distanceLeft.toString(), true)
    }

    private fun setDistanceAnimation(from: Float, to: Float) {
        animator?.cancel()
        animator?.removeAllUpdateListeners()

        animator = ValueAnimator.ofFloat(from, to)
        animator?.duration = TriviaViewModel.DISTANCE_DELTA
        animator?.addUpdateListener {
            liveLottieRoute?.progress = it.animatedValue as Float
        }
        animator?.start()
    }

    override fun onDestroy() {
        super.onDestroy()

        animator?.cancel()
        animator?.removeAllUpdateListeners()
    }

}