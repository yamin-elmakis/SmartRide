package com.example.smartride.widgets

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.animation.Animation
import android.widget.LinearLayout
import com.example.smartride.R
import com.example.smartride.base.IBottomNavigation
import kotlinx.android.synthetic.main.navigation_bottom.view.*
import android.view.animation.AnimationUtils


class BottomNavigation : LinearLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private var pendingTab: Tab? = null

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        orientation = HORIZONTAL
        gravity = Gravity.CENTER_HORIZONTAL
        setBackgroundColor(Color.parseColor("#383c45"))

        inflate(context, R.layout.navigation_bottom, this)

        tabBoard.setOnClickListener {
            clearSelected()
            it.isSelected = true
            (context as? IBottomNavigation)?.let {
                it.onLeaderBoardClicked()
            }
        }
        tabRide.setOnClickListener {
            clearSelected()
            it.isSelected = true
            (context as? IBottomNavigation)?.let {
                it.onRideClicked()
            }
        }
        tabWallet.setOnClickListener {
            clearSelected()
            it.isSelected = true
            (context as? IBottomNavigation)?.let {
                it.onWalletClicked()
            }
        }

        pendingTab?.let {
            chooseTab(it)
        }

        setRideLiveState(true)
    }

    private fun chooseTab(tab: Tab){
        when (tab) {
            Tab.LEADER_BOARD -> tabBoard.performClick()
            Tab.RIDE -> tabRide.performClick()
            Tab.WALLET -> tabWallet.performClick()
        }
    }

    private fun clearSelected() {
        tabBoard.isSelected = false
        tabRide.isSelected = false
        tabWallet.isSelected = false
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        removeAllViews()
    }

    fun setRideLiveState(isLive: Boolean) {
//        val liveRideAnimation = AnimationUtils.loadAnimation(context, R.anim.live_ride_tab)
//        liveRideAnimation.repeatCount = Animation.INFINITE
//        liveRideAnimation.repeatMode = Animation.RESTART
//        rideIconBg.startAnimation(liveRideAnimation)
    }

    fun seteSelectedTab(tab: Tab) {
        if (isAttachedToWindow) {
            chooseTab(tab)
        } else {
            pendingTab = tab
        }
    }

    enum class Tab {
        LEADER_BOARD,
        RIDE,
        WALLET
    }

}