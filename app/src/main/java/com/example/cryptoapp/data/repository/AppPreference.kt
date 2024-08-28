package com.example.cryptoapp.data.repository

import android.content.SharedPreferences
import com.example.cryptoapp.domain.repository.AppPreferenceRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreference @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : AppPreferenceRepo {
    companion object {
        private const val SELECTED_CURRENCY: String = "SELECTED_CURRENCY"
    }

    override fun saveSelectedCurrency(selectedCurrency: String) {
        sharedPreferences.edit().putString(SELECTED_CURRENCY, selectedCurrency).apply()

    }

    override fun getLastSelectedCurrency(): String {
        return sharedPreferences.getString(SELECTED_CURRENCY, "USD") ?: "USD"
    }

}

@Singleton
class AppPreferenceRoom @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : AppPreferenceRepo {
    companion object {
        private const val SELECTED_CURRENCY: String = "SELECTED_CURRENCY"
    }

    override fun saveSelectedCurrency(selectedCurrency: String) {
        sharedPreferences.edit().putString(SELECTED_CURRENCY, selectedCurrency).apply()

    }

    override fun getLastSelectedCurrency(): String {
        return sharedPreferences.getString(SELECTED_CURRENCY, "USD") ?: "USD"
    }

}