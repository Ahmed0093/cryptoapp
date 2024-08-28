package com.example.cryptoapp.domain.repository

interface AppPreferenceRepo {
    fun saveSelectedCurrency(selectedCurrency :String)
    fun getLastSelectedCurrency():String
}