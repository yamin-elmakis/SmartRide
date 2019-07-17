package com.example.smartride.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.smartride.R
import kotlinx.android.synthetic.main.fragment_home.*
import lib.yamin.easylog.EasyLog

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pendingTimer.setTime(System.currentTimeMillis() + 75 * 1000)
        pendingStartInfo.setOnClickListener {
            EasyLog.e("open")
            pendingRideLayout.transitionToState(R.id.explanation_open)
        }
        pendingExplanationInfo.setOnClickListener {
            EasyLog.e("close")
            pendingRideLayout.transitionToState(R.id.explanation_close)
        }
    }
}
