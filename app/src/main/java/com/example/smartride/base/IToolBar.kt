package com.example.smartride.base

import com.example.smartride.widgets.MainToolBar

interface IToolBar {

    fun toggleToolBar(show: Boolean)
    fun setToolBarMode(toolBarMode: MainToolBar.ToolBarMode)
    fun onToolBarBackPress()

}