package com.pwr.trainwithme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mainViewModel
            by lazy { ViewModelProviders.of(this)[MainViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Set of top level destinations - don't show up arrow in toolbar in there
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.offersFragment, R.id.homeFragment, R.id.locationFragment)
        )
        NavigationUI.setupWithNavController(
            toolbar,
            findNavController(R.id.nav_host_fragment),
            appBarConfiguration
        )

        bottomNavigationView.setupWithNavController(findNavController(R.id.nav_host_fragment))

        mainViewModel.isAuthenticated.observe(this){
            if(!it) findNavController(R.id.nav_host_fragment).navigate(R.id.authActivity)
        }
    }

    /**
     * Handles up arrow in toolbar
     */
    override fun onSupportNavigateUp(): Boolean =
        findNavController(R.id.nav_host_fragment).navigateUp()
                || super.onSupportNavigateUp()
}
