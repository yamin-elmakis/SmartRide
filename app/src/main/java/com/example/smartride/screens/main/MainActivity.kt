package com.example.smartride.screens.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.smartride.R
import com.example.smartride.base.IToolBar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), IToolBar {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        } // Else, need to wait for onRestoreInstanceState
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)

        // val navGraphIds = listOf(R.navigation.navigation_main, R.navigation.list, R.navigation.form)
        val controller = findNavController(R.id.nav_host_fragment)

        // Setup the bottom navigation view with a list of navigation graphs
        bottomNavigationView.setupWithNavController(controller)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp()
    }

    override fun toggleToolBar(show: Boolean) {
        
    }

}
