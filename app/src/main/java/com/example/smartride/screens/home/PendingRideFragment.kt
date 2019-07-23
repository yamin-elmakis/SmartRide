package com.example.smartride.screens.home

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
import com.example.smartride.base.IBottomNavigation
import com.example.smartride.screens.main.MainActivity
import com.example.smartride.screens.trivia.RideState
import com.example.smartride.screens.trivia.TriviaViewModel
import com.example.smartride.screens.trivia.TriviaViewModelFactory
import com.example.smartride.utils.changed
import com.example.smartride.widgets.TimeCounterView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.view_user_score.*
import lib.yamin.easylog.EasyLog
import java.text.SimpleDateFormat

class PendingRideFragment : BaseFragment(), TimeCounterView.TimerCallbacks, ValueAnimator.AnimatorUpdateListener,
    ValueEventListener {

    private var animator: ValueAnimator? = null
    private var timestampReference: DatabaseReference? = null
    private lateinit var triviaVM: TriviaViewModel
    var lastState: RideState = RideState(-1, -1, -1, 0)

    override fun displayToolBar() = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        triviaVM = ViewModelProviders.of(requireActivity(), TriviaViewModelFactory()).get(TriviaViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pendingHi.text = "Hey " + (FirebaseAuth.getInstance().currentUser?.displayName ?: "")

        timestampReference = FirebaseDatabase.getInstance().getReference("nextTaxi/timestamp")
        timestampReference?.addValueEventListener(this)

        triviaVM.rideData.observe(this, Observer {
            handleState(it)
        })

        val disposable = MainActivity.userScore.subscribe({
            userScore.text = it.toString()
        },{
            EasyLog.e("userScore error: $it")
        })

        compositeDisposable.add(disposable)
    }

    private fun handleState(state: RideState) {
        state.changed(lastState, { startRideTime }, action = {
            startTimer(it)
        })

        lastState = state
    }

    private fun startTimer(millis: Long) {
        val duration = millis - System.currentTimeMillis()

        pendingTimer.setTime(millis)
        pendingStartInfo.setOnClickListener {
            EasyLog.e("open")
            pendingExplainContainer.transitionToState(R.id.explanation_open)
        }
        pendingExplanationInfo.setOnClickListener {
            EasyLog.e("close")
            pendingExplainContainer.transitionToState(R.id.explanation_close)
        }
        pendingLottieRoute.imageAssetsFolder = "assets/";
        pendingLottieRoute.setAnimation("on_the_way.json")

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
        EasyLog.d("ifDone: $time, ${(time <= 0)}")
        return if (time <= 0) {
            (activity as? IBottomNavigation)?.setRideLiveState(true)
            val navController = NavHostFragment.findNavController(this)
            navController.navigate(R.id.action_pendingRideFragment_to_liveRideFragment)
            true
        } else {
            (activity as? IBottomNavigation)?.setRideLiveState(false)
            false
        }
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
//        EasyLog.d("TEST_ANIM Val: ${animation?.animatedValue} - View: $pendingLottieRoute")
        pendingLottieRoute?.progress = animation?.animatedValue as Float
    }

    override fun onDataChange(dataSnapshot: DataSnapshot) {
        try {
            val timestamp = dataSnapshot.getValue(String::class.java)!!
            EasyLog.e("newTimestamp: $timestamp")
            triviaVM.updateStartRideTime(SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timestamp).time)
        } catch (e: Exception) {
            EasyLog.e(e)
            triviaVM.updateStartRideTime(System.currentTimeMillis() + 10*1000)
        }
    }

    override fun onCancelled(dataSnapshot: DatabaseError) {

    }
}
