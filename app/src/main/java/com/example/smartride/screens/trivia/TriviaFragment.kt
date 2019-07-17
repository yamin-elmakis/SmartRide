package com.example.smartride.screens.trivia

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.smartride.R
import com.example.smartride.base.BaseFragment
import com.example.smartride.widgets.MainToolBar

class TriviaFragment : BaseFragment() {

    private lateinit var triviaVM: TriviaViewModel
    override fun toolBarMode() = MainToolBar.ToolBarMode.BACK

    override fun onAttach(context: Context) {
        super.onAttach(context)
        triviaVM = ViewModelProviders.of(requireActivity(), TriviaViewModelFactory()).get(TriviaViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_trivia, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}