package com.bylaew.exytepokeapi.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bylaew.exytepokeapi.ui.info.AboutFragment
import com.bylaew.exytepokeapi.ui.info.StatsFragment

class ViewPageAdapter(fa: FragmentActivity)  : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {

        return when(position){
            0 -> {
                return AboutFragment()
            }
            1 -> {
                return StatsFragment()

            }
            else -> StatsFragment()
        }
    }
}