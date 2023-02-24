package com.alibasoglu.cinemax

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.alibasoglu.cinemax.core.fragment.ToolbarConfiguration
import com.alibasoglu.cinemax.customviews.CustomToolbar
import com.alibasoglu.cinemax.databinding.ActivityMainBinding
import com.alibasoglu.cinemax.utils.navigateSafe
import com.alibasoglu.cinemax.utils.viewbinding.viewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupNavigation()
    }

    private fun setupNavigation() {
        navController =
            (supportFragmentManager.findFragmentById(binding.navigationHostFragment.id) as NavHostFragment).navController
        val bottomNavigationView: BottomNavigationView = binding.bottomNav
        val chipNavigationBar: ChipNavigationBar = binding.chipNav
        chipNavigationBar.setOnItemSelectedListener { itemId ->
            bottomNavigationView.selectedItemId = itemId
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            chipNavigationBar.setItemSelected(
                destination.id
            )
        }
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }

    fun navBack() {
        navController.navigateUp()
    }

    fun nav(directions: NavDirections, onError: (() -> Unit)? = null) {
        navController.navigateSafe(directions, onError)
    }

    fun configureToolbar(toolbarConfiguration: ToolbarConfiguration?) {
        binding.toolbar.configure(toolbarConfiguration)
    }

    fun getToolbar(): CustomToolbar = binding.toolbar

    fun hideBottomNavbar() {
        binding.chipNav.visibility = View.GONE
    }

    fun showBottomNavBar() {
        binding.chipNav.visibility = View.VISIBLE
    }

}
