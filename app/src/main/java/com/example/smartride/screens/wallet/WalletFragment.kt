package com.example.smartride.screens.wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smartride.R
import com.example.smartride.base.BaseFragment
import com.example.smartride.widgets.MainToolBar

class WalletFragment : BaseFragment() {

    override fun displayToolBar(): Boolean = true

    override fun toolBarMode(): MainToolBar.ToolBarMode = MainToolBar.ToolBarMode.DETAILS

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_wallet, container, false)
    }
}