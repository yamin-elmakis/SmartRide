package com.example.smartride.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.smartride.widgets.MainToolBar
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment : Fragment() {

    open fun displayToolBar() = true

    open fun toolBarMode() = MainToolBar.ToolBarMode.DETAILS

    protected val compositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.clear()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as? IToolBar)?.let {
            it.toggleToolBar(displayToolBar())
            it.setToolBarMode(toolBarMode())
        }
    }

}