package com.example.tabsensorlab

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

// Adapter that supplies fragments to ViewPager2
// and controls which fragment is shown for each tab
class MyViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    // Returns the total number of fragments (tabs)
    override fun getItemCount(): Int = 3

    // Creates and returns the fragment corresponding to the given position
    override fun createFragment(position: Int): Fragment {
        return when (position) {

            // Position 0: Home tab
            0 -> HomeFragment()

            // Position 1: Status tab (light sensor)
            1 -> StatusFragment()

            // Position 2: Settings tab
            else -> SettingFragment()
        }
    }
}
