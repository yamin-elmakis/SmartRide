package com.example.smartride.screens.main

import android.os.Bundle
import android.view.View
import androidx.annotation.NavigationRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.smartride.R
import com.example.smartride.base.IBottomNavigation
import com.example.smartride.base.IToolBar
import com.example.smartride.widgets.BottomNavigation
import com.example.smartride.widgets.MainToolBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IToolBar, IBottomNavigation {

    companion object {
        private const val KEY_SELECTED_GRAPH = "key_selected_graph"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            bottomNavigation.seteSelectedTab(BottomNavigation.Tab.RIDE)
        }
    }

//    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
//        super.onRestoreInstanceState(savedInstanceState)
//        // Now that BottomNavigationBar has restored its instance state
//        // and its selectedItemId, we can proceed with setting up the
//        // BottomNavigationBar with Navigation
//        setupBottomNavigationBar()
//    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp()
    }

    override fun toggleToolBar(show: Boolean) {
        if (show) {
            mainToolbar.visibility = View.VISIBLE
        } else {
            mainToolbar.visibility = View.GONE
        }
    }

    override fun setToolBarMode(toolBarMode: MainToolBar.ToolBarMode) {
        mainToolbar.setToolBarMode(toolBarMode)
    }

    override fun onToolBarBackPress() {
        onBackPressed()
    }

    override fun onLeaderBoardClicked() {
        setUpNavigation(R.navigation.navigation_leaders)
    }

    override fun onRideClicked() {
        setUpNavigation(R.navigation.navigation_main)
    }

    override fun onWalletClicked() {
        setUpNavigation(R.navigation.navigation_wallet)
    }

    private fun setUpNavigation(@NavigationRes naviagationGraph: Int) {
        val navHost = nav_host_fragment as NavHostFragment
        val graph = navHost.navController.navInflater.inflate(naviagationGraph)

        navHost.navController.graph = graph


//        NavigationUI.setupActionBarWithNavController(this, navHost.navController)
    }

}
