package com.example.smartride.screens.home

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.example.smartride.R
import com.example.smartride.base.BaseFragment
import com.example.smartride.widgets.TimeCounterView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home.*
import lib.yamin.easylog.EasyLog
import java.text.SimpleDateFormat

class PendingRideFragment : BaseFragment(), TimeCounterView.TimerCallbacks, ValueAnimator.AnimatorUpdateListener,
    ValueEventListener {

    private var animator: ValueAnimator? = null
    private var timestampReference: DatabaseReference? = null

    override fun displayToolBar() = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timestampReference = FirebaseDatabase.getInstance().getReference("nextTaxi/timestamp")
        timestampReference?.addValueEventListener(this)
    }

    private fun startTimer(millis: Long) {
        val duration = millis - System.currentTimeMillis()

        pendingTimer.setTime(millis)
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

        if (duration > 0) {
            animator = ValueAnimator.ofFloat(0f, 1f)
            animator?.duration = duration
            animator?.addUpdateListener(this)
            animator?.start()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        animator?.removeUpdateListener(this)
        timestampReference?.removeEventListener(this)
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
        EasyLog.d("TEST_ANIM Val: ${animation?.animatedValue} - View: $pendingLottieRoute")
        pendingLottieRoute?.progress = animation?.animatedValue as Float
    }

    override fun onDataChange(dataSnapshot: DataSnapshot) {
        try {
            val timestamp = dataSnapshot.getValue(String::class.java)
            val millis = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timestamp).time
            startTimer(millis)
        } catch (e: Exception) {
            EasyLog.e(e)
        }
    }

    override fun onCancelled(dataSnapshot: DatabaseError) {

    }
}
