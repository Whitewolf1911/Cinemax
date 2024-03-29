package com.alibasoglu.cinemax

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import com.alibasoglu.cinemax.core.fragment.ToolbarConfiguration
import com.alibasoglu.cinemax.customviews.CustomToolbar
import com.alibasoglu.cinemax.databinding.ActivityMainBinding
import com.alibasoglu.cinemax.utils.ENGLISH
import com.alibasoglu.cinemax.utils.ProgressDialog
import com.alibasoglu.cinemax.utils.navigateSafe
import com.alibasoglu.cinemax.utils.setAppLocale
import com.alibasoglu.cinemax.utils.viewbinding.viewBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    private val binding by viewBinding(ActivityMainBinding::inflate)

    private val viewModel by viewModels<MainViewModel>()

    lateinit var myIntent: Intent

    private var currentLanguage = ENGLISH

    private var progressDialog = ProgressDialog(this)

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        installSplashScreen()
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.primary_dark)
        myIntent = intent
        currentLanguage = viewModel.getCurrentLocale()
        setAppLocale(currentLanguage)
        setContentView(binding.root)
        viewModel.getSetImagesConfigData()

        //Setting genre list from asset
        getGenresList(this, currentLanguage)?.let {
            GenresData.genres = it
        }
        setupNavigation()
        handleOnBoarding()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ContextWrapper(newBase?.setAppLocale(currentLanguage)))
    }

    private fun handleOnBoarding() {
        if (viewModel.getShouldShowOnBoarding()) {
            val graph = navController.navInflater.inflate(R.navigation.main_navigation)
            graph.setStartDestination(R.id.onboardingFragment)
            navController.setGraph(graph, intent.extras)
        }
    }

    private fun setupNavigation() {
        navController =
            (supportFragmentManager.findFragmentById(binding.navigationHostFragment.id) as NavHostFragment).navController
        binding.bottomNavigationView.apply {
            setupWithNavController(navController)
        }
    }

    fun setStartDestinationToIntro() {
        val graph = navController.navInflater.inflate(R.navigation.main_navigation)
        graph.setStartDestination(R.id.introFragment)
        navController.setGraph(graph, intent.extras)
    }

    fun setStartDestinationToHome() {
        val graph = navController.navInflater.inflate(R.navigation.main_navigation)
        graph.setStartDestination(R.id.homeFragment)
        navController.setGraph(graph, intent.extras)
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

    fun restartActivity() {
        finish()
        startActivity(myIntent)
    }

}
