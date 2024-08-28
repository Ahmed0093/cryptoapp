package com.example.cryptoapp.ui

sealed interface ViewState<out T> {
    data class ResultUiModel<out T>(val uiModel: T?) : ViewState<T>
    class ErrorUiModel(val error: String) : ViewState<Nothing>
    data object ShowLoading : ViewState<Nothing>
}