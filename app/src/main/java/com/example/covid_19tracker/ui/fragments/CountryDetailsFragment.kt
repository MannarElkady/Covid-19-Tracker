package com.example.covid_19tracker.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.covid_19tracker.R
import com.example.covid_19tracker.viewModels.CountryDetailsViewModel
import com.example.covid_19tracker.database.LocalCountryHistory
import com.example.covid_19tracker.domain.CountryModel
import com.github.ivbaranov.mfb.MaterialFavoriteButton.OnFavoriteChangeListener
import com.soywiz.klock.DateTime
import kotlinx.android.synthetic.main.country_details_fragment.*
import timber.log.Timber


class CountryDetailsFragment : Fragment() {

    private lateinit var country: CountryModel
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
        changeSubscription()
        viewModel = ViewModelProviders.of(this).get(CountryDetailsViewModel::class.java)
        // TODO: Use the ViewModel
        viewModel.getCountryData()?.observe(viewLifecycleOwner,Observer<CountryModel>{
            it?.let {
                country = it
                setUpCountryData(it)
                //setUpSubscriptionButton(it.isSubscribed)
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

    fun setUpSubscriptionButton(isSubscribed : Boolean){
        subscribeButton.isFavorite = isSubscribed
    }
    private fun changeSubscription() {
        subscribeButton.setOnFavoriteChangeListener(
            OnFavoriteChangeListener { buttonView, favorite ->
                country?.let {
                    viewModel.updateCountrySubscribe(country,favorite)
                }
            })
    }

    private fun setUpCountryData(CountryModel: CountryModel) {
        deathTodayTextView.setText("Today's Death: ${CountryModel.todayDeaths.toString()}")
        casesTodayTextView.setText("Today's Cases: ${CountryModel.todayCases.toString()}")
        totalRecoveredTextView.setText(CountryModel.recovered.toString())
        totalDeathsTextView.setText(CountryModel.deaths.toString())
        totalCasesTextView.setText(CountryModel.cases.toString())
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
