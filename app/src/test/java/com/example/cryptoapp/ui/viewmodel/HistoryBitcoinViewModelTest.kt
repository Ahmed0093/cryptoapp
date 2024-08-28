package com.example.cryptoapp.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cryptoapp.domain.ClientException
import com.example.cryptoapp.domain.model.Bitcoin
import com.example.cryptoapp.domain.useCase.GetHistoryBitcoinPricesUseCase
import com.example.cryptoapp.testconfig.TestDispatcher
import com.example.cryptoapp.ui.ViewState
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.wheneverBlocking

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HistoryBitcoinViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val getHistoryBitcoinPricesUseCase: GetHistoryBitcoinPricesUseCase =
        Mockito.mock(GetHistoryBitcoinPricesUseCase::class.java)
    private lateinit var viewModel: HistoryBitcoinViewModel

    @Before
    fun setUp() {
        viewModel = HistoryBitcoinViewModel(getHistoryBitcoinPricesUseCase, TestDispatcher())
    }

    //
    @Test
    fun `fetchHistory updates bitcoinHistoryData`() = runTest {
        // Given
        val expectedBitcoinData = listOf(Bitcoin("40000", "12T", "Bitcoin", "BTC", "USD"))
        wheneverBlocking { getHistoryBitcoinPricesUseCase.invoke() }
            .then { expectedBitcoinData }

        // When
        viewModel.fetchHistory()

        // Then
        val actualBitcoinData = (viewModel.uiState.value as? ViewState.ResultUiModel)?.uiModel
        assertEquals(expectedBitcoinData, actualBitcoinData)
    }

    @Test
    fun `when  fetchHistory Fail with Client Exception then View state is Error `() = runTest {
        // Given
        wheneverBlocking { getHistoryBitcoinPricesUseCase.invoke() }
            .then { throw ClientException() }

        // When
        viewModel.fetchHistory()

        // Then
        val actualBitcoinData = (viewModel.uiState.value as? ViewState.ErrorUiModel)
        assertEquals(actualBitcoinData is ViewState.ErrorUiModel, true)
    }

}
