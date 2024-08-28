package com.example.cryptoapp.ui.screens

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cryptoapp.R
import com.example.cryptoapp.domain.model.Bitcoin
import com.example.cryptoapp.ui.CircleLoaderUI
import com.example.cryptoapp.ui.ErrorUI
import com.example.cryptoapp.ui.Screen
import com.example.cryptoapp.ui.SimplifiedDropdownCurrencySelector
import com.example.cryptoapp.ui.ViewState
import com.example.cryptoapp.ui.findActivity
import com.example.cryptoapp.ui.viewmodel.BitcoinViewModel

@SuppressLint("SuspiciousIndentation")
@Composable
fun MainScreen(navController: NavController) {

    val context = LocalContext.current
    BackHandler {
        context.findActivity()?.finish()
    }
    val bitcoinViewModel: BitcoinViewModel = hiltViewModel()
    val uiState by bitcoinViewModel.uiState.collectAsState()
    val selectedCurrency by bitcoinViewModel.selectedCurrency.collectAsState()

    when (uiState) {

        is ViewState.ShowLoading -> {
            CircleLoaderUI()
        }

        is ViewState.ResultUiModel -> {
            val bitcoin = (uiState as ViewState.ResultUiModel<Any?>)?.uiModel as Bitcoin
            MainScreenUi(
                bitcoin, selectedCurrency, onOptionSelected = { currency ->
                    bitcoinViewModel.saveSelectedCurrency(currency)
                    bitcoinViewModel.fetchBitcoinPrice()

                }, refreshApi = {
                    bitcoinViewModel.fetchBitcoinPrice()
                }, navigateToAllCoinsScreen = {
                    navController.navigate(Screen.AllCoins.name)
                    bitcoinViewModel.cancelRepeatTask()
                }, navigateToHistoryCoinsScreen = {
                    navController.navigate(Screen.History.name)
                    bitcoinViewModel.cancelRepeatTask()
                })
        }

        is ViewState.ErrorUiModel -> {
            ErrorUI(onRefreshClick = {
                bitcoinViewModel.fetchBitcoinPrice()
            })
        }
    }
}

@Composable
fun MainScreenUi(
    bitcoin: Bitcoin,
    selectedCurrency: String,
    onOptionSelected: (String) -> Unit,
    refreshApi: () -> Unit,
    navigateToAllCoinsScreen: () -> Unit,
    navigateToHistoryCoinsScreen: () -> Unit = {},

    ) {
    val currencies = listOf("USD", "EUR", "GBP")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.vectoric),
            contentDescription = "Logo",
            modifier = Modifier.scale(0.5f)
        )
        SimplifiedDropdownCurrencySelector(
            label = "Select Currency",
            options = currencies,
            selectedOption = selectedCurrency,
            onOptionSelected = { selected ->
                onOptionSelected(selected)
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "${bitcoin.name} Price: ${bitcoin.priceUsd} $selectedCurrency",
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = "Timestamp: ${bitcoin.timestamp}",
            style = MaterialTheme.typography.labelLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            refreshApi()
        }) {
            Text(text = stringResource(R.string.refresh))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navigateToAllCoinsScreen() }) {
            Text(text = stringResource(R.string.view_allcoins_rates))
        }
        Button(onClick = { navigateToHistoryCoinsScreen() }) {
            Text(text = stringResource(R.string.show_history_graph))
        }
    }
}

