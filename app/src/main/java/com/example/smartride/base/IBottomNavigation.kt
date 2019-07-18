package com.example.smartride.base

interface IBottomNavigation {

    fun onLeaderBoardClicked()
    fun onRideClicked()
    fun onWalletClicked()

    fun setRideLiveState(isLive: Boolean)

}