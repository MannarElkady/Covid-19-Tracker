package com.example.covid_19tracker.utils

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.example.covid_19tracker.R
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

//abstract class PreferenceDelegate<T> : ReadWriteProperty<Any, T> {
//    companion object {
//        var dayNightMode by StringPreferenceDelegate(applicationContext.getString(R.string.preference_day_night_mode_key))
//        var firstTimeLaunch by BooleanPreferenceDelegate(applicationContext.getString(R.string.preference_app_launch_key))
//    }
//
//    protected val sharedPreferences: SharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(applicationContext) }
//}
//
//class StringPreferenceDelegate(private val key: String, private val defaultValue: String = "") : PreferenceDelegate<String>() {
//    override fun getValue(thisRef: Any, property: KProperty<*>) = sharedPreferences.getString(key, defaultValue)!!
//    override fun setValue(thisRef: Any, property: KProperty<*>, value: String) = sharedPreferences.edit { putString(key, value) }
//}
//
//class IntPreferenceDelegate(private val key: String, private val defaultValue: Int = 0) : PreferenceDelegate<Int>() {
//    override fun getValue(thisRef: Any, property: KProperty<*>) = sharedPreferences.getInt(key, defaultValue)
//    override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) = sharedPreferences.edit { putInt(key, value) }
//}
