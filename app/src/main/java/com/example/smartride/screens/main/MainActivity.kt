package com.example.smartride.screens.main

import android.os.Bundle
import android.view.View
import androidx.annotation.NavigationRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.smartride.R
import com.example.smartride.base.IBottomNavigation
import com.example.smartride.base.IToolBar
import com.example.smartride.screens.trivia.TriviaViewModel
import com.example.smartride.screens.trivia.TriviaViewModelFactory
import com.example.smartride.widgets.BottomNavigation
import com.example.smartride.widgets.MainToolBar
import com.google.firebase.database.*
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_main.*
import lib.yamin.easylog.EasyLog

class MainActivity : AppCompatActivity(), IToolBar, IBottomNavigation, ValueEventListener {

    private var timestampReference: DatabaseReference? = null

    companion object {
        private const val KEY_SELECTED_GRAPH = "key_selected_graph"

        var userScore = BehaviorSubject.create<Int>().apply {
            onNext(0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            bottomNavigation.seteSelectedTab(BottomNavigation.Tab.RIDE)
        }

        val viewModel = ViewModelProviders.of(this, TriviaViewModelFactory()).get(TriviaViewModel::class.java)

        timestampReference = FirebaseDatabase.getInstance().getReference("userScore")
        timestampReference?.addValueEventListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        timestampReference?.removeEventListener(this)
    }

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

    override fun setRideLiveState(isLive: Boolean) {
        bottomNavigation.setRideLiveState(isLive)
    }

    private fun setUpNavigation(@NavigationRes naviagationGraph: Int) {
        val navHost = nav_host_fragment as NavHostFragment
        val graph = navHost.navController.navInflater.inflate(naviagationGraph)

        navHost.navController.graph = graph


//        NavigationUI.setupActionBarWithNavController(this, navHost.navController)
    }

    override fun onDataChange(dataSnapshot: DataSnapshot) {
        try {
            userScore.onNext(dataSnapshot.getValue(Int::class.java) ?: 0)
        } catch (e: Exception) {
            EasyLog.e(e)
        }
    }

    override fun onCancelled(p0: DatabaseError) {

    }

}
