package com.example.smartride.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.smartride.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    var counter:Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        timer.setTime(System.currentTimeMillis() + 75 * 1000)
        text.setOnClickListener {
            textFlipper.animateSetText((++counter).toString())
        }
    }
}
