package com.example.cryptoapp.ui.screens

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.cryptoapp.domain.model.Bitcoin
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainScreenKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController
    val expectedBitcoinData = Bitcoin("40000", "12T", "Bitcoin", "BTC", "USD")

    @Before
    fun setupNavHost() {

        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            MainScreenUi(
                bitcoin = expectedBitcoinData,
                selectedCurrency = "USD",
                onOptionSelected = {},
                refreshApi = {},
                navigateToAllCoinsScreen = {})
        }
    }

    @Test
    fun bitcoinScreen_showsBitcoinData() {

        // Then
        composeTestRule.onNodeWithText("Refresh").assertExists()
        composeTestRule.onNodeWithText(("View AlLCoins Rates")).assertExists()
        composeTestRule.onNodeWithText("BTC Price: 40000 USD").assertExists()
    }
}
