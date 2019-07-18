package com.example.smartride.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.smartride.R
import com.example.smartride.base.IToolBar
import com.example.smartride.screens.main.MainActivity
import com.google.android.material.appbar.AppBarLayout
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.toolbar_main.view.*
import kotlinx.android.synthetic.main.view_user_score.view.*

class MainToolBar : AppBarLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private var toolBarMode: ToolBarMode? = null

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        inflate(context, R.layout.toolbar_main, this)

        textTitle.text = context.getString(R.string.hi_user_toolbar, FirebaseAuth.getInstance().currentUser?.displayName ?: "User")

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

        userScore.text = MainActivity.userScore.toString()
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