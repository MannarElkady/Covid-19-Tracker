package com.example.covid_19tracker.UI

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.covid_19tracker.R
import com.example.covid_19tracker.ViewModels.CountryDetailsViewModel

class CountryDetails : Fragment() {

    companion object {
        fun newInstance() = CountryDetails()
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
        viewModel = ViewModelProviders.of(this).get(CountryDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
