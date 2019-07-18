package com.example.smartride.screens.live

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.airbnb.lottie.LottieDrawable
import com.example.smartride.R
import com.example.smartride.base.BaseFragment
import com.example.smartride.screens.trivia.RideState
import com.example.smartride.screens.trivia.TriviaViewModel
import com.example.smartride.screens.trivia.TriviaViewModelFactory
import com.example.smartride.utils.changed
import kotlinx.android.synthetic.main.fragment_live_ride.*
import lib.yamin.easylog.EasyLog

class LiveRideFragment : BaseFragment() {

    private lateinit var triviaVM: TriviaViewModel
    var lastState = RideState(-1, 0, 0)

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
            EasyLog.e()

            val navController = NavHostFragment.findNavController(this)
            navController.navigate(R.id.action_liveRideFragment_to_stagesFragment)
        }

        liveLottieRoute.imageAssetsFolder = "assets/";
        liveLottieRoute.setAnimation("on_the_ride.json")
        liveLottieRoute.repeatCount = LottieDrawable.INFINITE
        liveLottieRoute.playAnimation()

        triviaVM.rideData.observe(this, Observer {
            it.changed(lastState, { distanceToDestination }, action = {
                liveDistanceLeft.animateSetText("$it")
            })

        })
    }

    fun setDistanceLeft(distanceLeft: Int) {
        liveDistanceLeft.animateSetText(distanceLeft.toString())
    }
}