package com.example.smartride.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.smartride.base.IToolBar
import com.example.smartride.screens.main.MainActivity
import com.example.smartride.screens.trivia.RideState
import com.example.smartride.screens.trivia.TriviaViewModel
import com.example.smartride.screens.trivia.TriviaViewModelFactory
import com.example.smartride.utils.changed
import com.google.android.material.appbar.AppBarLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.toolbar_main.view.*
import kotlinx.android.synthetic.main.view_user_score.view.*
import lib.yamin.easylog.EasyLog
import java.text.SimpleDateFormat
import java.util.*


class MainToolBar : AppBarLayout {

    private lateinit var triviaVM: TriviaViewModel
    var lastState: RideState = RideState(-1, -1, -1, -1)
    private var lastStartRideTime: Long = 0
    private val compositeDisposable = CompositeDisposable()

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private var toolBarMode: ToolBarMode? = null

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        inflate(context, com.example.smartride.R.layout.toolbar_main, this)

        triviaVM =
            ViewModelProviders.of(context as MainActivity, TriviaViewModelFactory()).get(TriviaViewModel::class.java)
        textTitle.text = context.getString(
            com.example.smartride.R.string.hi_user_toolbar,
            FirebaseAuth.getInstance().currentUser?.displayName ?: "User"
        )

        updateToolBarMode()

        imageBack.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN,
                MotionEvent.ACTION_MOVE -> {
                    v.alpha = 0.5f
                }
                else -> {
                    v.alpha = 1f
                }
            }

            return@setOnTouchListener false
        }
        imageBack.setOnClickListener {
            (context as? IToolBar)?.let {
                it.onToolBarBackPress()
            }
        }

        userScoreContainer.setOnClickListener {
            (context as? IToolBar)?.let {
                it.onUserScorePress()
            }
        }

        triviaVM.rideData.observe(context as MainActivity, Observer {
            handleRideState(it)
        })

        toolbarSwitchTime.setOnClickListener {
            var updatedTime: Long =
                if (System.currentTimeMillis() > lastStartRideTime) {
                    1
                } else {
                    -1
                }
            EasyLog.e("toUpdate: $updatedTime")
            updatedTime = System.currentTimeMillis() + updatedTime * 180 * 1000
            EasyLog.e("updatedTime: $updatedTime")
            triviaVM.updateStartRideTime(updatedTime)

            val date = Date(updatedTime)
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

            val fireData = formatter.format(date)
            EasyLog.e("fireData: $fireData")
            FirebaseDatabase.getInstance().getReference("nextTaxi/timestamp").setValue(fireData)

        }

        val disposable = MainActivity.userScore.subscribe({
            userScore.text = it.toString()
        },{

        })

        compositeDisposable.add(disposable)
    }

    private fun handleRideState(state: RideState) {
        state.changed(lastState, { startRideTime }, action = {
            lastStartRideTime = it
        })

        lastState = state
    }

    fun updateUserScore(score: Int) {
        if (isAttachedToWindow) {
            userScore.text = score.toString()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        removeAllViews()


    }

    fun setToolBarMode(mode: ToolBarMode) {
        toolBarMode = mode
        if (isAttachedToWindow) {
            updateToolBarMode()
        }
    }

    private fun updateToolBarMode() {
        toolBarMode?.let {
            when (it) {
                ToolBarMode.DETAILS -> {
                    groupDetails.visibility = View.VISIBLE
                    imageBack.visibility = View.GONE
                }
                ToolBarMode.BACK -> {
                    imageBack.visibility = View.VISIBLE
                    groupDetails.visibility = View.GONE
                }
            }

        }
    }

    enum class ToolBarMode {
        DETAILS,
        BACK
    }

}