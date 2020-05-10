package com.example.covid_19tracker.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.example.covid_19tracker.R
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class PreferenceDelegate<T> : ReadWriteProperty<Any, T> {
    private lateinit var applicationContext: Application


        var darkMode by BooleanPreferenceDelegate("dark_mode")
        var language by BooleanPreferenceDelegate("lan")


    protected val sharedPreferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(
            applicationContext
        )
    }
}

class StringPreferenceDelegate(private val key: String, private val defaultValue: String = "") :
    PreferenceDelegate<String>() {
    override fun getValue(thisRef: Any, property: KProperty<*>) =
        sharedPreferences.getString(key, defaultValue)!!

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String) =
        sharedPreferences.edit { putString(key, value) }
}

class BooleanPreferenceDelegate(
    private val key: String,
    private val defaultValue: Boolean = false
) : PreferenceDelegate<Boolean>() {
    override fun getValue(thisRef: Any, property: KProperty<*>) =
        sharedPreferences.getBoolean(key, defaultValue)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) =
        sharedPreferences.edit { putBoolean(key, value) }
}
