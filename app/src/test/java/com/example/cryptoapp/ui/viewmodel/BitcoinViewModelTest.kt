package com.example.cryptoapp.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cryptoapp.data.repository.AppPreference
import com.example.cryptoapp.domain.ClientException
import com.example.cryptoapp.domain.model.Bitcoin
import com.example.cryptoapp.domain.useCase.GetBitcoinPriceUseCase
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
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.wheneverBlocking


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class BitcoinViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val getBitcoinPriceUseCase: GetBitcoinPriceUseCase =
        Mockito.mock(GetBitcoinPriceUseCase::class.java)
    private val appPreference: AppPreference = Mockito.mock(AppPreference::class.java)
    private lateinit var viewModel: BitcoinViewModel

    @Before
    fun setUp() {
        viewModel = BitcoinViewModel(getBitcoinPriceUseCase, appPreference, TestDispatcher())
    }

    //
    @Test
    fun `fetchBitcoinData updates bitcoinData`() = runTest {
        // Given
        val expectedBitcoinData = Bitcoin("40000", "12T", "Bitcoin", "BTC", "USD")
        wheneverBlocking { getBitcoinPriceUseCase.invoke() }
            .then { expectedBitcoinData }

        // When
        viewModel.fetchBitcoinPrice()

        // Then
        val actualBitcoinData = (viewModel.uiState.value as? ViewState.ResultUiModel)?.uiModel
        assertEquals(expectedBitcoinData, actualBitcoinData)
    }

    @Test
    fun `when  fetchBitcoinData Fail with Client Exception then View state is Error `() = runTest {
        // Given
        wheneverBlocking { getBitcoinPriceUseCase.invoke() }
            .then { throw ClientException() }

        // When
        viewModel.fetchBitcoinPrice()

        // Then
        val actualBitcoinData = (viewModel.uiState.value as? ViewState.ErrorUiModel)
        assertEquals(actualBitcoinData is ViewState.ErrorUiModel, true)
    }

    @Test
    fun `when saveSelectedCurrency called make sure that appPreference is called`() = runTest {
        // Given
        val currency = "EUR"

        // When
        viewModel.saveSelectedCurrency(currency)

        //Then
        verify(appPreference, times(1)).saveSelectedCurrency(currency)
        val actualCurrency = viewModel.selectedCurrency.value
        assertEquals(actualCurrency, currency)
    }
}
