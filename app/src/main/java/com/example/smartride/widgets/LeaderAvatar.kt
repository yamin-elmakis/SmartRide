package com.example.smartride.widgets

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.smartride.R
import com.example.smartride.screens.board.LeaderBoardFragment
import kotlinx.android.synthetic.main.avatar_leader.view.*

class LeaderAvatar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.avatar_leader, this)
    }

//
//
//    constructor(context: Context, isTop: Boolean, isMe: Boolean, useLargeIcon: Boolean) : super(context) {
//
//        this.isTop = isTop
//        this.isMe = isMe
//        this.useLargeAvatar = useLargeIcon
//
//        init(null)
//    }
//
//    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
//        init(attrs)
//    }
//
//    private fun init(attrs: AttributeSet?) {
//
//        inflate(context, R.layout.avatar_leader, this)
//
//        attrs?.let {
//            val typedArray = context.obtainStyledAttributes(it, R.styleable.LeaderAvatar)
//            try {
//                isTop = typedArray.getBoolean(R.styleable.LeaderAvatar_isTop, false)
//                isMe = typedArray.getBoolean(R.styleable.LeaderAvatar_isMe, false)
//                useLargeAvatar = typedArray.getBoolean(R.styleable.LeaderAvatar_useLargeIcon, false)
//            } catch (e: Exception) {
//
//            } finally {
//                typedArray.recycle()
//            }
//        }
//
//        setData()
//    }

    fun setData(isTop: Boolean, useLargeAvatar: Boolean, position: Int, user: LeaderBoardFragment.LeaderUser) {

        if (user.isMe) {
            textName.setTypeface(null, Typeface.BOLD)
            textScore.setTypeface(null, Typeface.BOLD)
        } else {
            textName.setTypeface(null, Typeface.NORMAL)
            textScore.setTypeface(null, Typeface.NORMAL)
        }

        if (useLargeAvatar) {
            groupAvatarLarge.visibility = View.VISIBLE
            groupAvatar.visibility = View.GONE
        } else {
            groupAvatar.visibility = View.VISIBLE
            groupAvatarLarge.visibility = View.GONE
        }

        if (isTop) {
            badge.setBackgroundResource(R.drawable.ic_leader_badge_top)
            badgeLarge.setBackgroundResource(R.drawable.ic_leader_badge_top)
            badge.setTextColor(ContextCompat.getColor(context, R.color.white))
            badgeLarge.setTextColor(ContextCompat.getColor(context, R.color.white))
        } else {
            badge.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
            badgeLarge.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
            if (user.isMe) {
                badge.setBackgroundResource(R.drawable.ic_leader_badge_me)
                badgeLarge.setBackgroundResource(R.drawable.ic_leader_badge_me)
            } else {
                badge.setBackgroundResource(R.drawable.ic_leader_badge)
                badgeLarge.setBackgroundResource(R.drawable.ic_leader_badge)
            }
        }

        imageAvatar.setImageResource(user.avatar)
        imageAvatarLarge.setImageResource(user.avatar)

        badge.text = position.toString()
        badgeLarge.text = position.toString()

        textName.text = user.name

        textScore.text = user.score.toString()
    }

}