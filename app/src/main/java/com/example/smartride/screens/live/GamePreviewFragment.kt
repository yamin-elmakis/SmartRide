package com.example.smartride.screens.live

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.example.smartride.R
import com.example.smartride.base.BaseFragment
import com.example.smartride.screens.trivia.RideState
import com.example.smartride.screens.trivia.TriviaViewModel
import com.example.smartride.screens.trivia.TriviaViewModelFactory
import com.example.smartride.utils.changed
import com.example.smartride.widgets.MainToolBar
import kotlinx.android.synthetic.main.fragment_game_preview.*

class GamePreviewFragment : BaseFragment() {

    private lateinit var triviaVM: TriviaViewModel
    var lastState = RideState(-1, -1, 0)
    companion object {
        const val DELAY = 800L
    }

    override fun toolBarMode() = MainToolBar.ToolBarMode.BACK

    override fun onAttach(context: Context) {
        super.onAttach(context)
        triviaVM = ViewModelProviders.of(requireActivity(), TriviaViewModelFactory()).get(TriviaViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game_preview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        triviaVM.rideData.observe(this, Observer { state ->
            state.changed(lastState, { currentStage }, action = {
                setStage("Stage ${it + 1}")
            })
            state.changed(lastState, { distanceToDestination }, action = {
                previewDistanceLeft.setText(it.toString(), true)
            })
            lastState = state
        })
    }

    private fun setStage(stage: String) {
        previewStage?.text = stage
        previewStage?.animate()
            ?.setDuration(300)
            ?.setStartDelay(700)
            ?.scaleX(2f)
            ?.scaleY(2f)
            ?.alpha(0f)
            ?.withEndAction {
                setReady()
            }
    }

    private fun setReady() {
        previewStage?.text = "Ready"
        previewStage?.scaleX = 1f
        previewStage?.scaleY = 1f
        previewStage?.alpha = 1f

        previewStage?.animate()
            ?.setDuration(300)
            ?.setStartDelay(DELAY)
            ?.scaleX(2f)
            ?.scaleY(2f)
            ?.alpha(0f)
            ?.withEndAction {
                setSet()
            }
    }

    private fun setSet() {
        previewStage?.text = "Set"
        previewStage?.scaleX = 1f
        previewStage?.scaleY = 1f
        previewStage?.alpha = 1f

        previewStage?.animate()
            ?.setDuration(300)
            ?.setStartDelay(DELAY)
            ?.scaleX(2f)
            ?.scaleY(2f)
            ?.alpha(0f)
            ?.withEndAction {
                setGo()
            }
    }

    private fun setGo() {
        previewStage?.text = "Go"
        previewStage?.scaleX = 1f
        previewStage?.scaleY = 1f
        previewStage?.alpha = 1f

        previewStage?.animate()
            ?.setDuration(300)
            ?.setStartDelay(DELAY)
            ?.scaleX(2f)
            ?.scaleY(2f)
            ?.alpha(0f)
            ?.withEndAction {
                previewStage?.let {
                    val navController = NavHostFragment.findNavController(this)
                    navController.navigate(R.id.action_gamePreviewFragment_to_triviaFragment)
                }
            }
    }
}