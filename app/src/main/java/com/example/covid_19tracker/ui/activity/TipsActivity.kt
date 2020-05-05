package com.example.covid_19tracker.ui.activity

import com.example.covid_19tracker.R
import com.stephentuso.welcome.BasicPage
import com.stephentuso.welcome.TitlePage
import com.stephentuso.welcome.WelcomeActivity
import com.stephentuso.welcome.WelcomeConfiguration

class TipsActivity : WelcomeActivity() {

    override fun configuration(): WelcomeConfiguration {
    return WelcomeConfiguration.Builder(this)
        .defaultBackgroundColor(R.color.primaryLightColor)
        .page(TitlePage(
            R.drawable.ic_covid,
            getString(R.string.app_name)
        ))
        .page( BasicPage(
            R.drawable.stay_home,
            getString(R.string.stay_home),"")
            .background(R.color.secondaryTextColor)
        )
        .page( BasicPage(
            R.drawable.medical,
            getString(R.string.keep_distance),
            "")
            .background(R.color.orange)
        )
        .page( BasicPage(
            R.drawable.symbol,
            getString(R.string.wash_hand),"")
            .background(R.color.primaryColor)
        )
        .page( BasicPage(
            R.drawable.cover_mouth,
            getString(R.string.cover_mouth),"")
            .background(R.color.primaryLightColor)
        )
        .swipeToDismiss(true)
            .build()
    }
}
