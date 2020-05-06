package com.example.covid_19tracker.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.covid_19tracker.CovidApplication
import com.example.covid_19tracker.R
import com.example.covid_19tracker.database.CountyEntity
import com.example.covid_19tracker.database.LocalCountryInfo
import com.example.covid_19tracker.repository.Repository
import com.example.covid_19tracker.ui.adapters.CountryAdapter
import com.example.covid_19tracker.ui.adapters.CountryListener
import com.example.covid_19tracker.viewModels.HomeViewModel
import com.example.covid_19tracker.databinding.HomeFragmentBinding
import com.example.covid_19tracker.network.CountryInfo
import com.example.covid_19tracker.repository.RepositoryContract
import com.example.covid_19tracker.utils.Order
import com.google.android.material.chip.Chip
import timber.log.Timber


class HomeFragment : Fragment() {


    private val viewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory((requireContext().applicationContext as CovidApplication).repository)
    }
    private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Get a reference to the binding object and inflate the fragment views.
         binding = DataBindingUtil.inflate(
            inflater, R.layout.home_fragment, container, false
        )
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)
        val adapter =
            CountryAdapter(CountryListener { countryName -> viewModel.onCountryClicked(countryName) })
        binding.countryList.adapter = adapter
        viewModel.navigateToCountryDetails.observe(viewLifecycleOwner, Observer{
            val countryEntity = CountyEntity("Manar",
                LocalCountryInfo(1,"","","",0.0,0.0),3232,453,234,34223,42342,444,44,
                3942323,333,444)
            val action = HomeFragmentDirections.actionHomeFragmentToCountryDetails(countryEntity)
            findNavController().navigate(action)
            viewModel.doneNavigating()
        })
        initChipGroup()
        viewModel.countryList.observe(viewLifecycleOwner, Observer {
            //Timber.v(it[0].cases.toString())
        })
        return binding.root
    }

    private fun initChipGroup() {
        val chipGroup = binding.filterList
        val inflator = LayoutInflater.from(chipGroup.context)
        val orderList = resources.getStringArray(R.array.order_list)
        val children = orderList.mapIndexed {index, filterName ->
            val chip = inflator.inflate(R.layout.filter, chipGroup, false) as Chip
            chip.text = filterName
            chip.tag = index
            chip.setOnCheckedChangeListener { button, isChecked ->
                viewModel.onFilterChanged(button.tag as Int, isChecked)
            }
            chip
        }

//        chipGroup.removeAllViews()
//
        for (chip in children) {
            chipGroup.addView(chip)
        }
    }


}

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(
    private val repository: RepositoryContract
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (HomeViewModel(repository) as T)
}
