package com.example.smartride.screens.live

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smartride.R
import com.example.smartride.base.BaseFragment
import com.example.smartride.widgets.MainToolBar
import kotlinx.android.synthetic.main.fragment_game_preview.*

class GamePreviewFragment : BaseFragment() {

    override fun toolBarMode() = MainToolBar.ToolBarMode.BACK

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game_preview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        liveSingleCta.setOnClickListener {
//            EasyLog.e()
//        }
        setStage("Stage 2")
    }

    private fun setStage(stage: String) {
        previewStage.text = stage
        previewStage.animate()
            .setDuration(300)
            .setStartDelay(500)
            .scaleX(2f)
            .scaleY(2f)
            .alpha(0f)
            .withEndAction {
                setReady()
            }
    }

    private fun setReady() {
        previewStage.text = "Ready"
        previewStage.animate()
            .setDuration(300)
            .setStartDelay(500)
            .scaleX(2f)
            .scaleY(2f)
            .alpha(0f)
            .withEndAction {

            }
    }
}