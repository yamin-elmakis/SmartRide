package com.example.smartride.screens.live

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smartride.R
import com.example.smartride.base.BaseFragment
import com.example.smartride.widgets.MainToolBar
import kotlinx.android.synthetic.main.fragment_stages.*
import lib.yamin.easylog.EasyLog

class StagesFragment : BaseFragment() {

    override fun toolBarMode() = MainToolBar.ToolBarMode.BACK

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stagesLottieStages.setOnClickListener {
            EasyLog.e()
        }
    }
}