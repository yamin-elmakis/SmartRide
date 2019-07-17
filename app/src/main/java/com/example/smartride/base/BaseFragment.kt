package com.example.smartride.base

import android.os.Bundle
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    open fun displayToolBar() = true

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as? IToolBar)?.let {
            it.toggleToolBar(displayToolBar())
        }
    }

}