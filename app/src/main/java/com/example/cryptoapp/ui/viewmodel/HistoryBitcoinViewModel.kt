package com.example.cryptoapp.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.data.CoroutineDispatcher
import com.example.cryptoapp.domain.useCase.GetHistoryBitcoinPricesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryBitcoinViewModel @Inject constructor(
    private val getHistoryBitcoinPricesUseCase: GetHistoryBitcoinPricesUseCase,
    private val dispatcher: CoroutineDispatcher
) : BaseViewModel() {

    init {
        fetchHistory()
    }

    fun fetchHistory() {
        viewModelScope.launch(dispatcher.IO + handler) {
            enqueueApiCall {
                getHistoryBitcoinPricesUseCase.invoke()
            }
        }
    }
}