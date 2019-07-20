package com.example.smartride.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.widget.AppCompatTextView

class FlipTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    fun setText(text: CharSequence, animated: Boolean) {
        if (animated) {
            animateSetText(text.toString())
        } else {
            setText(text)
        }
    }

    private fun animateSetText(newText: String) {
        animate()
            .setDuration(450)
            .rotationX(90f)
            .setInterpolator(AccelerateInterpolator())
            .withEndAction {
                text = newText
                animate()
                    .setDuration(450)
                    .rotationX(0f)
                    .setInterpolator(DecelerateInterpolator())
                    .withStartAction {
                        rotationX= -90f
                    }
                    .start()
            }.start()
    }
}