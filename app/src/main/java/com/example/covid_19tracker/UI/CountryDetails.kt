package com.example.covid_19tracker.UI

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.covid_19tracker.R
import com.example.covid_19tracker.ViewModels.CountryDetailsViewModel
import timber.log.Timber

class CountryDetails : Fragment() {

    companion object {
        fun newInstance() = CountryDetails()
    }

    private lateinit var viewModel: CountryDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(CountryDetailsViewModel::class.java)
        viewModel.countryHistory?.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d("history",it.toString())
            }
        })
        return inflater.inflate(R.layout.country_details_fragment, container, false)

    }


}
