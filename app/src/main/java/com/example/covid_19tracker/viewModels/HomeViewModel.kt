package com.example.covid_19tracker.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.covid_19tracker.repository.RepositoryContract
import com.example.covid_19tracker.utils.Order
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel(private val repository: RepositoryContract) : ViewModel() {
    val countryList = repository.countryList

    private var _navigateToDetails = MutableLiveData<String>()
    private var filter = FilterHolder()

    /**
     * If this is non-null, immediately navigate to [CountryDetailsFragment] and call [doneNavigating]
     */
    val navigateToCountryDetails: LiveData<String>
        get() = _navigateToDetails


    init {
        viewModelScope.launch {
            repository.refreshCountries()
        }
    }


    /**
     * Call this immediately after navigating to [CountryDetailsFragment]
     *
     * It will clear the navigation request, so if the user rotates their phone it won't navigate
     * twice.
     */
    fun doneNavigating() {
        _navigateToDetails.value = null
    }

    fun onCountryClicked(countryName: String) {
        _navigateToDetails.value = countryName
    }

    fun onFilterChanged(order: Int, isChecked: Boolean) {
        Timber.d(isChecked.toString())
        if (this.filter.update(order, isChecked)) {
            viewModelScope.launch {
              //  repository.orderList(order)
            }
        }
    }

    private class FilterHolder {
        var currentValue: Int? = null
            private set

        fun update(changedFilter: Int, isChecked: Boolean): Boolean {
            if (isChecked) {
                currentValue = changedFilter
                return true
            } else if (currentValue == changedFilter) {
                currentValue = null
                return true
            }
            return false
        }
    }
}
