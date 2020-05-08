package com.example.covid_19tracker.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.example.covid_19tracker.R
import com.example.covid_19tracker.domain.CountryModel
import com.example.covid_19tracker.viewModels.CountryDetailsViewModel
import com.github.ivbaranov.mfb.MaterialFavoriteButton.OnFavoriteChangeListener
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.soywiz.klock.DateTime
import kotlinx.android.synthetic.main.country_details_fragment.*


class CountryDetailsFragment : Fragment() {

    private val args: CountryDetailsFragmentArgs by navArgs()

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
        country = args.countryData
        country?.let {
            setUpCountryData(it)
            viewModel.setCountryEntity(country)
            setUpSubscriptionButton(it.country)
        }

        viewModel.getCountryHistory().observe(viewLifecycleOwner, Observer {
            setUpCardWithData(caseChart,it.timeline.cases.keys.toList().takeLast(7),it.timeline.cases.values.toList().takeLast(7))
            setUpCardWithData(deathChart,it.timeline.deaths.keys.toList().takeLast(7),it.timeline.deaths.values.toList().takeLast(7))
            setUpCardWithData(recoveredChart,it.timeline.recovered.keys.toList().takeLast(7),it.timeline.recovered.values.toList().takeLast(7))
        })
    }



    private fun setUpCardWithData(lineChart: LineChart,xAxisList : List<String>, yAxisList : List<Long>){
        val entries = ArrayList<Entry>()
        xAxisList.forEachIndexed { index, s ->
            entries.add(Entry(index.toFloat(),yAxisList[index].toFloat()))
        }

        val vl = LineDataSet(entries, "Latest Case History")

        vl.setDrawValues(false)
        vl.setDrawFilled(true)
        vl.lineWidth = 3f
        vl.fillColor = R.color.primaryLightColor
        vl.fillAlpha = R.color.darkColor

        lineChart.xAxis.labelRotationAngle = 0f
        lineChart.data = LineData(vl)

        val formatYList = xAxisList.takeLast(7).map {
            it.split("/").subList(0,2).joinToString("/")
        }.toList()

        lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(formatYList)

        lineChart.axisRight.isEnabled = false
        lineChart.getAxisLeft().axisMinimum = yAxisList.min()!!.toFloat();
        lineChart.getAxisLeft().axisMaximum = yAxisList.max()!!.toFloat()+ 0.1f;

        lineChart.setTouchEnabled(true)
        lineChart.setPinchZoom(true)

        lineChart.description.text = "Days"
        caseChart.setNoDataText("No forex yet!")

        lineChart.animateX(1800, Easing.EaseInExpo)
    }

    fun setUpSubscriptionButton(countryName: String){
        viewModel.isCountrySubscribed(countryName).observe(viewLifecycleOwner, Observer {
            it?.let {
                subscribeButton.isFavorite = true
            }
        })
    }

    private fun changeSubscription() {
        subscribeButton.setOnFavoriteChangeListener(
            OnFavoriteChangeListener { buttonView, favorite ->
                country?.let {
                    if (favorite) {
                        viewModel.addCountrySubscribed(it)
                    } else {
                        viewModel.deleteSubscribedCountry(it)
                    }
                }
            })
    }

    private fun setUpCountryData(countryModel: CountryModel) {
        deathTodayTextView.text = "Today's Death: ${countryModel.todayDeaths.toString()}"
        casesTodayTextView.text = "Today's Cases: ${countryModel.todayCases.toString()}"
        totalRecoveredTextView.text = countryModel.recovered.toString()
        totalDeathsTextView.text = countryModel.deaths.toString()
        totalCasesTextView.text = countryModel.cases.toString()
        countyName.text = countryModel.country
    }

    fun setDateTime(){
        dateTimeTextView.setText("Date: ${DateTime.now().date} \nTime: ${DateTime.now().time}")
    }

}
