package com.example.smartride.screens.board

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import com.example.smartride.R
import com.example.smartride.base.BaseFragment
import com.example.smartride.screens.main.MainActivity
import com.example.smartride.widgets.MainToolBar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_leader_board.*

class LeaderBoardFragment : BaseFragment() {

    override fun displayToolBar(): Boolean = true

    override fun toolBarMode(): MainToolBar.ToolBarMode = MainToolBar.ToolBarMode.DETAILS

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_leader_board, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = leaders.sortedBy { it.score }.asReversed()

        avatar1st.setData(isTop = true, useLargeAvatar = true, position = 1, user = list[0])
        avatar2nd.setData(isTop = false, useLargeAvatar = true, position = 2, user = list[1])
        avatar3rd.setData(isTop = false, useLargeAvatar = true, position = 3, user = list[2])

        avatar_4.setData(isTop = false, useLargeAvatar = false, position = 4, user = list[3])
        avatar_5.setData(isTop = false, useLargeAvatar = false, position = 5, user = list[4])
        avatar_6.setData(isTop = false, useLargeAvatar = false, position = 6, user = list[5])
        avatar_7.setData(isTop = false, useLargeAvatar = false, position = 7, user = list[6])
        avatar_8.setData(isTop = false, useLargeAvatar = false, position = 8, user = list[7])
        avatar_9.setData(isTop = false, useLargeAvatar = false, position = 9, user = list[8])
        avatar_10.setData(isTop = false, useLargeAvatar = false, position = 10, user = list[9])
        avatar_11.setData(isTop = false, useLargeAvatar = false, position = 11, user = list[10])
        avatar_12.setData(isTop = false, useLargeAvatar = false, position = 12, user = list[11])
        avatar_13.setData(isTop = false, useLargeAvatar = false, position = 13, user = list[12])
        avatar_14.setData(isTop = false, useLargeAvatar = false, position = 14, user = list[13])
        avatar_15.setData(isTop = false, useLargeAvatar = false, position = 15, user = list[14])

    }

    companion object {

        private val leaders = listOf(
            LeaderUser(name = "Sofie", avatar = R.drawable.ic_avatar_leader_1, score = 999),
            LeaderUser(name = "Harmen", avatar = R.drawable.ic_avatar_leader_2, score = 654),
            LeaderUser(name = "Emmy", avatar = R.drawable.ic_avatar_leader_emmy, score = 999),
            LeaderUser(name = "Julian", avatar = R.drawable.ic_avatar_leader_julian, score = 753),
            LeaderUser(name = "Kwak", avatar = R.drawable.ic_avatar_leader_kwak, score = 312),
            LeaderUser(name = "Tommi", avatar = R.drawable.ic_avatar_leader_2, score = 102),
            LeaderUser(name = "Jane", avatar = R.drawable.ic_avatar_leader_1, score = 269),
            LeaderUser(name = "Will", avatar = R.drawable.ic_avatar_leader_2, score = 735),
            LeaderUser(name = "Rick", avatar = R.drawable.ic_avatar_leader_2, score = 456),
            LeaderUser(name = "Morty", avatar = R.drawable.ic_avatar_leader_2, score = 152),
            LeaderUser(name = "Bruce", avatar = R.drawable.ic_avatar_leader_bruce, score = 847),
            LeaderUser(name = "Tony", avatar = R.drawable.ic_avatar_leader_2, score = 435),
            LeaderUser(name = "Peter", avatar = R.drawable.ic_avatar_leader_2, score = 112),
            LeaderUser(name = "Naomi", avatar = R.drawable.ic_avatar_leader_naomi, score = 353),
            LeaderUser(isMe = true, name = FirebaseAuth.getInstance().currentUser?.displayName ?: "", avatar = R.drawable.ic_avatar_leader_me, score = MainActivity.userScore)
        )

    }

    data class LeaderUser(
        val isMe: Boolean = false, val name: String, @DrawableRes val avatar: Int, val score: Int
    )
}