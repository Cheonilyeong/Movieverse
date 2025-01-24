package com.ilyeong.movieverse

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.forEach
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import androidx.navigation.navOptions
import com.ilyeong.movieverse.databinding.ActivityMainBinding
import com.ilyeong.movieverse.home.Home
import com.ilyeong.movieverse.home.HomeFragment
import com.ilyeong.movieverse.login.Login
import com.ilyeong.movieverse.login.LoginFragment
import com.ilyeong.movieverse.profile.Profile
import com.ilyeong.movieverse.profile.ProfileFragment
import com.ilyeong.movieverse.watchlist.Watchlist
import com.ilyeong.movieverse.watchlist.WatchlistFragment
import kotlinx.serialization.InternalSerializationApi

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @OptIn(InternalSerializationApi::class)
    @SuppressLint("RestrictedApi", "VisibleForTests")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        // create nav graph
        val navHostFragment = binding.navHostFragment.getFragment<NavHostFragment>()
        val navController = navHostFragment.navController

        navController.graph = navController.createGraph(
            startDestination = Home
        ) {
            fragment<LoginFragment, Login> {
                label = getString(R.string.label_login_title)
            }

            fragment<HomeFragment, Home> {
                label = getString(R.string.label_home_title)
            }

            fragment<WatchlistFragment, Watchlist> {
                label = getString(R.string.label_watchlist_title)
            }

            fragment<ProfileFragment, Profile> {
                label = getString(R.string.label_profile_title)
            }
        }

        // bottom navigation view setupWithNavController
        val navOptions = navOptions {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = false
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        binding.bnv.setOnItemSelectedListener { item ->
            val route = when (item.itemId) {
                R.id.nav_menu_home -> Home
                R.id.nav_menu_watchlist -> Watchlist
                R.id.nav_menu_profile -> Profile
                else -> return@setOnItemSelectedListener false
            }
            navController.navigate(route, navOptions)
            true
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bnv.menu.forEach { item ->
                val route = when (item.itemId) {
                    R.id.nav_menu_home -> Home
                    R.id.nav_menu_watchlist -> Watchlist
                    R.id.nav_menu_profile -> Profile
                    else -> return@forEach
                }
                /*
                WTF ?!
                item.isChecked = (destination.hierarchy.any { it.hasRoute(route::class) })
                why (item.isChecked) always become true?
                */
                if (destination.hierarchy.any { it.hasRoute(route::class) }) {
                    item.isChecked = true
                }
            }
        }

        // set system bar text color to white
        val insetsController = WindowInsetsControllerCompat(window, window.decorView)
        insetsController.isAppearanceLightStatusBars = false

        // set back pressed
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (!navController.popBackStack()) {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }
}