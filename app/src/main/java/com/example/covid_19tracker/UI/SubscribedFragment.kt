package com.example.covid_19tracker.UI

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.covid_19tracker.R
import com.example.covid_19tracker.ViewModels.SubscribedViewModel

class SubscribedFragment : Fragment() {

    companion object {
        fun newInstance() = SubscribedFragment()
    }

    private lateinit var viewModel: SubscribedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.subscribed_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SubscribedViewModel::class.java)
        // TODO: Use the ViewModel
    }

}