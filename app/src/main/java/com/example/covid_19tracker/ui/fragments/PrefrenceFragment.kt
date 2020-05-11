package com.example.covid_19tracker.ui.fragments

import android.content.Intent.getIntent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.*
import com.example.covid_19tracker.R
import com.example.covid_19tracker.ui.activity.MainActivity
import java.util.*


class PrefrenceFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var prefrenceScreen: PreferenceScreen
    private var count: Int = 0
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        prefrenceScreen = preferenceScreen
        sharedPreferences = preferenceScreen.sharedPreferences
        count = prefrenceScreen.preferenceCount
        // Go through all of the preferences, and set up their preference summary.
        for (i in 0 until count) {
            val prefrence = prefrenceScreen.getPreference(i)
            val value = sharedPreferences.getString(prefrence.key, "")
            prefrence.setSummary(value)
        }

    }

    override fun onSharedPreferenceChanged(p: SharedPreferences?, key: String?) {

        // Figure out which preference was changed
        val preference: Preference? = key?.let { findPreference(it) }

        preference?.let {
            // Updates the summary for the preference
            if (!(preference is SwitchPreferenceCompat)) {
                val value = sharedPreferences.getString(preference.key, "")
                setPreferenceSummary(preference, value!!)
            }
        }
        if (key != getString(R.string.enable_notification_key)) {
            setupLocal()
            requireActivity().finish()
            startActivity(requireActivity().intent)
        }
    }

    private fun setupLocal() {
        var change = ""
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val language = sharedPreferences.getString("language", "")
        if (language == "Ar") {
            change = "ar"
        } else {
            change = "en"
        }

        MainActivity.dLocale = Locale(change) //set any locale you want here
    }

    /**
     * Updates the summary for the preference
     *
     * @param preference The preference to be updated
     * @param value      The value that the preference was updated to
     */
    private fun setPreferenceSummary(
        preference: Preference,
        value: String
    ) {
        if (preference is ListPreference) {
            // For list preferences, figure out the label of the selected value
            val listPreference: ListPreference = preference
            val prefIndex: Int = listPreference.findIndexOfValue(value)
            if (prefIndex >= 0) {
                // Set the summary to that label
                listPreference.setSummary(listPreference.getEntries().get(prefIndex))
            }
        } else if (preference is SwitchPreferenceCompat) {
            //  set the summary to the value's simple string representation.
            preference.summary = value
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceScreen.sharedPreferences
            .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        preferenceScreen.sharedPreferences
            .unregisterOnSharedPreferenceChangeListener(this)
    }
}
