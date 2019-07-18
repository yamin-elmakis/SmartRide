package com.example.smartride.screens.wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.smartride.R
import com.example.smartride.base.BaseFragment
import com.example.smartride.widgets.MainToolBar
import kotlinx.android.synthetic.main.fragment_wallet.*


class WalletFragment : BaseFragment() {

    override fun displayToolBar(): Boolean = true

    override fun toolBarMode(): MainToolBar.ToolBarMode = MainToolBar.ToolBarMode.DETAILS

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_wallet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ViewPagerAdapter(childFragmentManager, listOf(Pair(BalanceFragment(), "Add Balance"), Pair(PointsFragment(), "Convert Points")))
        viewPager.adapter = adapter

        tabLayout.setupWithViewPager(viewPager)
    }

}

class ViewPagerAdapter(fragmentManager: FragmentManager, private val fragments: List<Pair<Fragment, String>>) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return fragments[position].first
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragments[position].second
    }
}

class BalanceFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val image = ImageView(context)
        image.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        image.setImageResource(R.drawable.fragment_add_balance)

        val scroll = ScrollView(context)
        scroll.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        scroll.addView(image)
        return scroll
    }
}

class PointsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val image = ImageView(context)
        image.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        image.setImageResource(R.drawable.fragment_convert_points)

        val scroll = ScrollView(context)
        scroll.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        scroll.addView(image)
        return scroll
    }
}