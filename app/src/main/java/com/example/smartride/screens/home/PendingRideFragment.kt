package com.example.smartride.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.example.smartride.R
import com.example.smartride.base.BaseFragment
import com.example.smartride.widgets.TimeCounterView
import kotlinx.android.synthetic.main.fragment_home.*
import lib.yamin.easylog.EasyLog
import android.animation.ValueAnimator

class PendingRideFragment : BaseFragment(), TimeCounterView.TimerCallbacks, ValueAnimator.AnimatorUpdateListener {

    private lateinit var animator: ValueAnimator

    override fun displayToolBar() = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val duration = System.currentTimeMillis() + 10 * 1000

        pendingTimer.setTime(duration)
        pendingStartInfo.setOnClickListener {
            EasyLog.e("open")
            pendingRideLayout.transitionToState(R.id.explanation_open)
        }
        pendingExplanationInfo.setOnClickListener {
            EasyLog.e("close")
            pendingRideLayout.transitionToState(R.id.explanation_close)
        }
        pendingLottieRoute.imageAssetsFolder = "assets/";
        pendingLottieRoute.setAnimation("on_the_way.json")
//        pendingLottieRoute.repeatCount = 0
//        pendingLottieRoute.playAnimation()

        animator = ValueAnimator.ofFloat(0f, 1f)
        animator.duration = duration
        animator.addUpdateListener(this)
        animator.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        animator.removeUpdateListener(this)
    }

    override fun onResume() {
        super.onResume()

        pendingTimer.getTime()?.let {
            if (checkIfDone(it)) {
                return
            }
        }
        pendingTimer.setListener(this)
    }

    private fun checkIfDone(time: Long): Boolean {

        if (time <= 0) {
            val navController = NavHostFragment.findNavController(this)
            navController.navigate(R.id.action_pendingRideFragment_to_liveRideFragment)
            return true
        }

        return false
    }

    override fun onPause() {
        super.onPause()

        pendingTimer.setListener(null)
    }

    override fun onTick(currentMillis: Long?) {
        currentMillis?.let {
            checkIfDone(it)
        }
    }

    override fun onAnimationUpdate(animation: ValueAnimator?) {
        EasyLog.d("TEST_ANIM Val: ${animation?.animatedValue as Float} - View: $pendingLottieRoute")
        pendingLottieRoute?.progress = animation?.animatedValue as Float
    }
}
