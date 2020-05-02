package com.example.covid_19tracker.UI.Fragments

import android.os.Build
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.example.covid_19tracker.Database.CountyEntity
import com.example.covid_19tracker.Database.LocalCountryHistory

import com.example.covid_19tracker.R
import com.example.covid_19tracker.ViewModels.CountryDetailsViewModel
import com.soywiz.klock.DateTime
import kotlinx.android.synthetic.main.country_details_fragment.*
import timber.log.Timber
import java.time.LocalDateTime

class CountryDetailsFragment : Fragment() {

    companion object {
        fun newInstance() =
            CountryDetailsFragment()
    }

    private lateinit var viewModel: CountryDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.country_details_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setDateTime()
        viewModel = ViewModelProviders.of(this).get(CountryDetailsViewModel::class.java)
        // TODO: Use the ViewModel
        viewModel.getCountryData()?.observe(viewLifecycleOwner,Observer<CountyEntity>{
            it?.let {
                setUpCountryData(it)
            }
        })
        viewModel.getCountryHistory().observe(viewLifecycleOwner,Observer<LocalCountryHistory>{
            setUICardsEmpty()
            it?.let {
                setUICards(it)
                Timber.w(it.toString())
            }
        })
    }

    private fun setUpCountryData(countyEntity: CountyEntity) {
        deathTodayTextView.setText("Today's Death: ${countyEntity.todayDeaths.toString()}")
        casesTodayTextView.setText("Today's Cases: ${countyEntity.todayCases.toString()}")
        totalRecoveredTextView.setText(countyEntity.recovered.toString())
        totalDeathsTextView.setText(countyEntity.deaths.toString())
        totalCasesTextView.setText(countyEntity.cases.toString())
    }

    fun setDateTime(){
        dateTimeTextView.setText("Date: ${DateTime.now().date} \nTime: ${DateTime.now().time}")
    }

    fun setUICardsEmpty(){
    }

    fun setUICards(localCountryHistory: LocalCountryHistory){
//        totalCasesTextView.setText(localCountryHistory.timeline.cases.values.last().toString())
//        totalDeathsTextView.setText(localCountryHistory.timeline.deaths.values.last().toString())
//        totalRecoveredTextView.setText(localCountryHistory.timeline.recovered.values.last().toString())
    }

}
