package com.example.covid_19tracker.UI.Fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.covid_19tracker.Database.CountyEntity
import com.example.covid_19tracker.Database.LocalCountryHistory
import com.example.covid_19tracker.NavigationGraphDirections

import com.example.covid_19tracker.R
import com.example.covid_19tracker.ViewModels.HomeViewModel
import kotlinx.android.synthetic.main.home_fragment.*
import timber.log.Timber

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() =
            HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private var selectedCountyEntity: CountyEntity? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        clickMeText.setOnClickListener({
            if(selectedCountyEntity != null){
                val detailsAction = NavigationGraphDirections
                    .actionGlobalCountryDetails(selectedCountyEntity!!)
                it.findNavController().navigate(R.id.action_global_countryDetails)
            }
        })
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
        viewModel.getCountryData()?.observe(viewLifecycleOwner, Observer<CountyEntity>{
            it?.let {
                selectedCountyEntity = it
            }
        })
    }

}
