package com.pwr.trainwithme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // TODO replace with MainActivityViewModel ?
    private val trainingProposalViewModel
            by lazy { ViewModelProviders.of(this)[TrainingProposalViewModel::class.java] }

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

        trainingProposalViewModel.isAuthenticated.observe(this){
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
