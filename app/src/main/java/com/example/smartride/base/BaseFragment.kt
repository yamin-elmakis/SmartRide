package com.example.smartride.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.smartride.widgets.MainToolBar

abstract class BaseFragment : Fragment() {

    open fun displayToolBar() = true

    open fun toolBarMode() = MainToolBar.ToolBarMode.DETAILS

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as? IToolBar)?.let {
            it.toggleToolBar(displayToolBar())
            it.setToolBarMode(toolBarMode())
        }
    }

}