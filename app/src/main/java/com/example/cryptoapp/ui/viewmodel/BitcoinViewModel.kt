package com.example.cryptoapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.data.CoroutineDispatcher
import com.example.cryptoapp.data.repository.AppPreference
import com.example.cryptoapp.domain.useCase.GetBitcoinPriceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BitcoinViewModel @Inject constructor(
    private val getBitcoinPriceUseCase: GetBitcoinPriceUseCase,
    private val appPreference: AppPreference,
    private val dispatcher: CoroutineDispatcher
) : BaseViewModel() {
    private val _selectedCurrency = MutableStateFlow<String>("USD")
    val selectedCurrency = _selectedCurrency.asStateFlow()
    private lateinit var repeatApiCall: Job
    private var repeatNumber = 0

    init {
        _selectedCurrency.value = appPreference.getLastSelectedCurrency()
        repeatApiCall = repeatApiFun()
    }

    fun fetchBitcoinPrice() {
        viewModelScope.launch(dispatcher.IO + handler) {
            enqueueApiCall {
                getBitcoinPriceUseCase.invoke()
            }
        }
    }

    private fun repeatApiFun(): Job {
        return viewModelScope.launch(dispatcher.IO + handler) {
            while (isActive) {
                repeatNumber++
                fetchBitcoinPrice()
                Log.d("++++ Calling", "API CALL REPEAT{$repeatNumber} ")
                delay(1000 * 60)
            }
        }
    }

    fun saveSelectedCurrency(currency: String) {
        viewModelScope.launch(dispatcher.IO) {
            appPreference.saveSelectedCurrency(currency)
            _selectedCurrency.value = currency
        }
    }

    fun cancelRepeatTask() {
        repeatApiCall.cancel()
    }
}
