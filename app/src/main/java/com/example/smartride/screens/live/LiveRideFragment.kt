package com.example.smartride.screens.live

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smartride.R
import com.example.smartride.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_live_ride.*
import lib.yamin.easylog.EasyLog

class LiveRideFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        liveSingleCta.setOnClickListener {
            EasyLog.e()
        }
    }

    fun setDistanceLeft(distanceLeft: Int) {
        liveDistanceLeft.animateSetText(distanceLeft.toString())
    }
}