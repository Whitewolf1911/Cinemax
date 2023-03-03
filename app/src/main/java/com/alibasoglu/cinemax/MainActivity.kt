package com.alibasoglu.cinemax

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.alibasoglu.cinemax.core.fragment.ToolbarConfiguration
import com.alibasoglu.cinemax.customviews.CustomToolbar
import com.alibasoglu.cinemax.databinding.ActivityMainBinding
import com.alibasoglu.cinemax.utils.ProgressDialog
import com.alibasoglu.cinemax.utils.navigateSafe
import com.alibasoglu.cinemax.utils.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    private val binding by viewBinding(ActivityMainBinding::inflate)

    private val viewModel by viewModels<MainViewModel>()

    private var progressDialog = ProgressDialog(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.getSetImagesConfigData()

        //Setting genre list from asset
        getGenresList(this)?.let {
            GenresData.genres = it
        }
        setupNavigation()
    }

    private fun setupNavigation() {
        navController =
            (supportFragmentManager.findFragmentById(binding.navigationHostFragment.id) as NavHostFragment).navController
        binding.bottomNavigationView.apply {
            setupWithNavController(navController)
            setOnItemReselectedListener {} // To prevent reselect item and resetting selected fragment
        }
    }

    fun setStartDestinationToHome() {
        val graph = navController.navInflater.inflate(R.navigation.main_navigation)
        graph.setStartDestination(R.id.homeFragment)
        navController.setGraph(graph, intent.extras)
        binding.bottomNavigationView.apply {
            setupWithNavController(navController)
            setOnItemReselectedListener {} // To prevent reselect item and resetting selected fragment
        }
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

    fun hideToolbar() {
        binding.toolbar.visibility = View.GONE
    }

    fun showToolbar() {
        binding.toolbar.visibility = View.VISIBLE
    }

    fun hideBottomNavbar() {
        binding.bottomNavigationView.visibility = View.GONE
    }

    fun showBottomNavBar() {
        binding.bottomNavigationView.visibility = View.VISIBLE
    }

    fun showProgressDialog() {
        progressDialog.startDialog()
    }

    fun hideProgressDialog() {
        progressDialog.dismissDialog()
    }

}
