package com.example.covid_19tracker.ui.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.covid_19tracker.R
import com.stephentuso.welcome.WelcomeHelper
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    var welcomeScreen: WelcomeHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        welcomeScreen = WelcomeHelper(this, TipsActivity::class.java)
        welcomeScreen!!.show(savedInstanceState)
        navController = findNavController(R.id.nav_host)

        //setupActionBarWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.navigation_menu,menu)
        menu?.let { bottomBar.setupWithNavController(it,navController) }
        return true
    }


  /*  override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()
        return true
    }*/

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        welcomeScreen?.onSaveInstanceState(outState);
    }

}
