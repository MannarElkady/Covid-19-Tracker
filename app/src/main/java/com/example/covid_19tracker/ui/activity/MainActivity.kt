package com.example.covid_19tracker.ui.activity

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.example.covid_19tracker.R
import com.stephentuso.welcome.WelcomeHelper
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    companion object {
        public var dLocale: Locale? = null
    }

    init {
        updateConfig()
    }
    private lateinit var navController: NavController
    var welcomeScreen: WelcomeHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupTheam()
        setContentView(R.layout.activity_main)
        welcomeScreen = WelcomeHelper(this, TipsActivity::class.java)
        welcomeScreen!!.show(savedInstanceState)
        navController = findNavController(R.id.nav_host)
        supportActionBar?.hide()
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.countryDetails) {
                bottomBar.visibility = View.GONE
            } else {
                bottomBar.visibility = View.VISIBLE
            }
        }

    }

    private fun setupTheam() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val themOn = sharedPreferences.getBoolean(getString(R.string.dark_mode_key), false)
        if (themOn)
            setTheme(R.style.Theme_MaterialComponents_DayNight_DarkActionBar)
        else
            setTheme(R.style.dark)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.navigation_menu, menu)
        menu?.let { bottomBar.setupWithNavController(it, navController) }
        return true
    }

    /* override fun onSupportNavigateUp(): Boolean {
         navController.navigateUp()
         return true
     }*/

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        welcomeScreen?.onSaveInstanceState(outState);
    }

    fun updateConfig() {
        if(dLocale==Locale("") ) // Do nothing if dLocale is null
            return

        Locale.setDefault(dLocale)
        val configuration = Configuration()
        configuration.setLocale(dLocale)
        this.applyOverrideConfiguration(configuration)
    }

}
