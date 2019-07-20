package com.example.smartride.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.animation.AnimationUtils
import android.widget.TextSwitcher
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.smartride.R

class FlipTextSwitcher @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : TextSwitcher(context, attrs) {
    init {
        setFactory {
            val text = TextView(context)
            text.setTextAppearance(R.style.CounterText);
            text
        }
        inAnimation = AnimationUtils.loadAnimation(context, R.anim.text_in)
        outAnimation = AnimationUtils.loadAnimation(context, R.anim.text_out)
    }
}