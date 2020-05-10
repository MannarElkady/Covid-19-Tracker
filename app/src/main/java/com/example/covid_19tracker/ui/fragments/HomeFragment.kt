package com.example.covid_19tracker.ui.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.covid_19tracker.CovidApplication
import com.example.covid_19tracker.R
import com.example.covid_19tracker.databinding.HomeFragmentBinding
import com.example.covid_19tracker.repository.RepositoryContract
import com.example.covid_19tracker.ui.adapters.CountryAdapter
import com.example.covid_19tracker.ui.adapters.CountryListener
import com.example.covid_19tracker.viewModels.HomeViewModel

import com.google.android.material.chip.Chip


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

//        //to be deleted
//        viewModel.country.observe(viewLifecycleOwner, Observer {
//        })
//        //to be deleted
        viewModel.navigateToCountryDetails.observe(viewLifecycleOwner, Observer {
            it?.let {
                val action = HomeFragmentDirections.actionHomeFragmentToCountryDetails(it)
                findNavController().navigate(action)
                viewModel.doneNavigating()
            }
        })
        viewModel.callHotline.observe(viewLifecycleOwner, Observer {
            it?.let {
                checkPermission()
                viewModel.finishCall()
            }
        })
        viewModel.orderdList.observe(viewLifecycleOwner, Observer {
            it?.let{

                adapter.submitList(it)
                binding.countryList.scrollToPosition(0)
            }
        })
        initChipGroup()
        return binding.root
    }

    private fun initChipGroup() {
        val chipGroup = binding.filterList
        val inflator = LayoutInflater.from(chipGroup.context)
        val orderList = resources.getStringArray(R.array.order_list)
        val children = orderList.mapIndexed { index, filterName ->
            val chip = inflator.inflate(R.layout.filter, chipGroup, false) as Chip
            chip.text = filterName
            chip.tag = index
            chip.chipIcon = when (index) {
                0 -> ResourcesCompat.getDrawable(resources, R.drawable.ic_covid, null)
                1 -> ResourcesCompat.getDrawable(resources, R.drawable.ic_death, null)
                else -> ResourcesCompat.getDrawable(resources, R.drawable.ic_recovery, null)
            }
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

    fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.CALL_PHONE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.CALL_PHONE
                )
            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.CALL_PHONE),
                    42
                )
            }
        } else {
            // Permission has already been granted
            callPhone()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode == 42) {
            // If request is cancelled, the result arrays are empty.
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // permission was granted, yay!
                callPhone()
            } else {
                // permission denied, boo! Disable the
                // functionality
            }
            return
        }
    }

    fun callPhone() {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "105"))
        startActivity(intent)
    }

}

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(
    private val repository: RepositoryContract
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (HomeViewModel(repository) as T)
}
