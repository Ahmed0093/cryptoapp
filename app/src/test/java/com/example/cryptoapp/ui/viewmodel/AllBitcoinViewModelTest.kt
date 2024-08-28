package com.example.cryptoapp.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cryptoapp.domain.ClientException
import com.example.cryptoapp.domain.model.Bitcoin
import com.example.cryptoapp.domain.useCase.GetAllBitcoinPriceUseCase
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
class AllBitcoinViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    //
    private val getAllBitcoinPriceUseCase: GetAllBitcoinPriceUseCase =
        Mockito.mock(GetAllBitcoinPriceUseCase::class.java)
    private lateinit var viewModel: AllBitcoinViewModel

    @Before
    fun setUp() {
        viewModel = AllBitcoinViewModel(getAllBitcoinPriceUseCase, TestDispatcher())
    }

    //
    @Test
    fun `fetchAllBitcoins updates uiState`() = runTest {
        // Given
        val expectedBitcoinData = listOf(Bitcoin("40000", "12T", "Bitcoin", "BTC", "USD"))
        wheneverBlocking { getAllBitcoinPriceUseCase.invoke() }
            .then { expectedBitcoinData }

        // When
        viewModel.fetchAllBitcoins()

        // Then
        val actualBitcoinData = (viewModel.uiState.value as? ViewState.ResultUiModel)?.uiModel
        assertEquals(expectedBitcoinData, actualBitcoinData)
    }

    @Test
    fun `when fetchAllBitcoins Fail with Client Exception then View state is Error `() = runTest {
        // Given
        wheneverBlocking { getAllBitcoinPriceUseCase.invoke() }
            .then { throw ClientException() }

        // When
        viewModel.fetchAllBitcoins()

        // Then
        val actualBitcoinData = (viewModel.uiState.value as? ViewState.ErrorUiModel)
        assertEquals(actualBitcoinData is ViewState.ErrorUiModel, true)
    }
}
