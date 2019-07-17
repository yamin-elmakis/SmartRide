package com.example.smartride.widgets

import android.content.Context
import android.util.AttributeSet
import com.example.smartride.R
import com.google.android.material.appbar.AppBarLayout
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.toolbar_main.view.*

class MainToolBar : AppBarLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        inflate(context, R.layout.toolbar_main, this)

        textTitle.text = context.getString(R.string.hi_user_toolbar, FirebaseAuth.getInstance().currentUser?.displayName ?: "User")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        removeAllViews()
    }

}