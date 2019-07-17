package com.example.smartride.screens.live

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.airbnb.lottie.LottieDrawable
import com.example.smartride.R
import com.example.smartride.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_live_ride.*
import lib.yamin.easylog.EasyLog

class LiveRideFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_live_ride, container, false)
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
    }

    fun setDistanceLeft(distanceLeft: Int) {
        liveDistanceLeft.animateSetText(distanceLeft.toString())
    }
}