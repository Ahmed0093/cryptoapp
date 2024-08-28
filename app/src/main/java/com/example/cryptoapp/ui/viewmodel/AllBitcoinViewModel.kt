package com.example.cryptoapp.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.data.CoroutineDispatcher
import com.example.cryptoapp.domain.useCase.GetAllBitcoinPriceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllBitcoinViewModel @Inject constructor(
    private val getAllBitcoinPriceUseCase: GetAllBitcoinPriceUseCase,
    private val dispatcher: CoroutineDispatcher
) : BaseViewModel() {

    init {
        fetchAllBitcoins()
    }

    fun fetchAllBitcoins() {
        viewModelScope.launch(dispatcher.IO + handler) {
            enqueueApiCall {
                getAllBitcoinPriceUseCase.invoke()
            }
        }
    }
}

