package com.example.covid_19tracker.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.covid_19tracker.domain.CountryModel
import com.example.covid_19tracker.repository.RepositoryContract
import com.squareup.moshi.Moshi
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel(private val repository: RepositoryContract) : ViewModel() {
    val countryList = repository.countryList
    private var _callHotLine = MutableLiveData<Boolean>()
    val callHotline: LiveData<Boolean>
        get() = _callHotLine
    private var _navigateToDetails = MutableLiveData<CountryModel>()
    private var filter = FilterHolder()
    private var _orderedList = MutableLiveData<List<CountryModel>>()

    val orderdList: LiveData<List<CountryModel>>
        get() = _orderedList

    /**
     * If this is non-null, immediately navigate to [CountryDetailsFragment] and call [doneNavigating]
     */
    val navigateToCountryDetails: LiveData<CountryModel>
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

    fun onCountryClicked(country: CountryModel) {
        Timber.d(country.toString())
        _navigateToDetails.value = country
    }

    fun onFilterChanged(order: Int, isChecked: Boolean) {
        Timber.d(isChecked.toString())
        if (this.filter.update(order, isChecked)) {
            viewModelScope.launch {
                when (order) {
                    0 -> orderListByCase()
                    1 -> orderListByDeath()
                    else -> orderListByRecovered()
                }
            }
        }
    }

    private fun orderListByCase() {

        _orderedList.postValue(countryList.value?.sortedByDescending { x -> x.cases })
    }

    private fun orderListByDeath() {

        _orderedList.postValue(countryList.value?.sortedByDescending { x -> x.deaths })
    }

    private fun orderListByRecovered() {

        _orderedList.postValue(countryList.value?.sortedByDescending { x -> x.recovered })
    }

    fun callHotLine() {
        _callHotLine.value = true
    }

    fun finishCall() {
        _callHotLine.value = null
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
