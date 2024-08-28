package com.example.cryptoapp.ui.screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.cryptoapp.domain.model.Bitcoin
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class AllCoinScreenKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val listOfBitCoins = listOf(Bitcoin("40000", "12T", "Bitcoin", "BTC", "USD"))

    @Before
    fun setupNavHost() {

        composeTestRule.setContent {
            AllCoinsContent(
                listOfBitCoins
            )
        }
    }

    @Test
    fun `allBitcoinScreen_showsBitcoinData`() {

        // Then
        composeTestRule.onNodeWithText("Bitcoin").assertExists()
        composeTestRule.onNodeWithText(("40000 USD")).assertExists()
        composeTestRule.onNodeWithText("BTC").assertExists()
    }
}