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
import com.airbnb.lottie.LottieDrawable
import kotlinx.android.synthetic.main.fragment_home.*
import lib.yamin.easylog.EasyLog

class BottomNavigation @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER_HORIZONTAL
        clipChildren = false
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
        EasyLog.e("isLive: $isLive")
        if (isLive) {
            tabLive.imageAssetsFolder = "assets/";
            tabLive.setAnimation("live_ride.json")
            tabLive.repeatCount = LottieDrawable.INFINITE
            tabLive.playAnimation()

            textRide.text = "Live Ride"
        } else {
            tabLive.pauseAnimation()
            tabLive.progress = 0.5f
            textRide.text = "Pending Ride"
        }
//        if (isLive) {
//            rideIconBg.animate()
//                .
//        }
//        val liveRideAnimation = AnimationUtils.loadAnimation(context, R.anim.live_ride_tab)
//        liveRideAnimation.repeatCount = Animation.INFINITE
//        liveRideAnimation.repeatMode = Animation.RESTART
//        rideIconBg.startAnimation(liveRideAnimation)
    }

    fun seteSelectedTab(tab: Tab) {
        chooseTab(tab)
    }

    enum class Tab {
        LEADER_BOARD,
        RIDE,
        WALLET
    }

}