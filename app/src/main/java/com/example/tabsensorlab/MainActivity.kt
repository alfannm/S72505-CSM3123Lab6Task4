package com.example.tabsensorlab

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

// MainActivity hosts the ViewPager2 and TabLayout
// and controls tab-based navigation between fragments
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load the main layout containing ViewPager2 and TabLayout
        setContentView(R.layout.activity_main)

        // Link ViewPager2 and TabLayout from XML
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)

        // Attach the custom adapter that supplies fragments to ViewPager2
        viewPager.adapter = MyViewPagerAdapter(this)

        // Connect TabLayout with ViewPager2
        // This ensures tab selection and page swipes stay in sync
        TabLayoutMediator(tabLayout, viewPager) { tab, pos ->

            // Set tab titles based on fragment position
            tab.text = listOf("Home", "Status", "Settings")[pos]

        }.attach()
    }
}
