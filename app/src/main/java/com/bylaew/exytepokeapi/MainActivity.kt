package com.bylaew.exytepokeapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController

import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.bylaew.exytepokeapi.ui.favorite.FavoriteFragment
import com.bylaew.exytepokeapi.ui.main.MainFragment
import com.bylaew.exytepokeapi.ui.search.SearchFragment
import com.google.android.material.navigation.NavigationBarView

import kotlinx.android.synthetic.main.main_activity.*






class MainActivity : AppCompatActivity() {

    lateinit var mainFragment: MainFragment
    lateinit var favoriteFragment: FavoriteFragment
    lateinit var searchFragment: SearchFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // doing bad deprecated things
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.statusBarColor = ContextCompat.getColor(this,R.color.colorPrimary)
        // probably end of doing bad things

        initCustomToolbar()



        bottomNavigationView.setupWithNavController(appNavHostFragment.findNavController())

        bottomNavigationView?.setOnItemSelectedListener {

                when (it.itemId) {
                    R.id.mainFragment -> {
                        appNavHostFragment.findNavController().navigate(R.id.mainFragment)

                        return@setOnItemSelectedListener true
                    }

                    R.id.savedFragment -> {
                        appNavHostFragment.findNavController().navigate(R.id.favoriteFragment)

                        return@setOnItemSelectedListener true
                    }

                    R.id.searchFragment -> {
                        appNavHostFragment.findNavController().navigate(R.id.searchFragment2)

                        return@setOnItemSelectedListener true
                    }

                }
                false
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.appNavHostFragment).navigateUp()
    }



    private fun initCustomToolbar() {
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM;
        supportActionBar?.setCustomView(R.layout.custom_toolbar)
    }
}