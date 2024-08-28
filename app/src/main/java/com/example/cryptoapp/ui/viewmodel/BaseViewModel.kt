package com.example.cryptoapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cryptoapp.ui.ViewState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel : ViewModel() {

    private val _uiState =
        MutableStateFlow<ViewState<Any?>>(ViewState.ShowLoading)
    val uiState = _uiState.asStateFlow()

    val handler = CoroutineExceptionHandler { _, exception ->
        _uiState.value = ViewState.ErrorUiModel(exception.message ?: "Error")
    }

    suspend fun <T : Any> enqueueApiCall(
        apiCall: suspend () -> T,
    ) {
        _uiState.value = ViewState.ShowLoading
        val result = apiCall.invoke()
        _uiState.value = ViewState.ResultUiModel(result)
    }

}




