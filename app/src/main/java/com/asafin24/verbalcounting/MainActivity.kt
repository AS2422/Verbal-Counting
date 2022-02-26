package com.asafin24.verbalcounting

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.asafin24.verbalcounting.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    private var navHostFragment: NavHostFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        APP = this

        navHostFragment = supportFragmentManager.findFragmentById(R.id.mainNavHostFragment) as? NavHostFragment ?: return
        navController = findNavController(R.id.mainNavHostFragment)

        val mainBottomNavView = findViewById<BottomNavigationView>(R.id.mainBottomNavView)
        mainBottomNavView.setupWithNavController(navController)
    }

    override fun onStart() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.menuMainFragment -> showBottomNav()
                R.id.setting -> showBottomNav()
                R.id.resultPractic -> showBottomNav()
                R.id.menuProgressFragment -> showBottomNav()
                R.id.practingSetting -> showBottomNav()
                else -> hideBottomNav()
            }
        }
        super.onStart()
    }




    private fun showBottomNav() {
        binding.mainBottomNavView.visibility = View.VISIBLE

    }

    private fun hideBottomNav() {
        binding.mainBottomNavView.visibility = View.GONE

    }


}