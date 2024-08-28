package com.example.cryptoapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cryptoapp.R
import com.example.cryptoapp.domain.model.Bitcoin
import com.example.cryptoapp.ui.AppBar
import com.example.cryptoapp.ui.CircleLoaderUI
import com.example.cryptoapp.ui.ErrorUI
import com.example.cryptoapp.ui.Screen
import com.example.cryptoapp.ui.ViewState
import com.example.cryptoapp.ui.viewmodel.AllBitcoinViewModel

const val TAG_STRING_BACK_BTN = "back_button"

@Composable
fun AllCoinsScreen(navController: NavController) {

    val allBitcoinViewModel: AllBitcoinViewModel = hiltViewModel()
    val uiState by allBitcoinViewModel.uiState.collectAsState()


    AppBar(
        title = stringResource(R.string.crypto_app),
        onLeadingIconClick = {
            navController.navigate(Screen.HOME.name) {
                popUpTo(Screen.AllCoins.name) {
                    inclusive = true

                }
            }
        },
        trailingIcon = { Text(
                text = stringResource(R.string.refresh),
                color = Color.Blue, modifier =Modifier.padding(top = 15.dp))
        }, onTrailingIconClick = {
            allBitcoinViewModel.fetchAllBitcoins()
        })
    Spacer(modifier = Modifier.height(16.dp))

    when (uiState) {

        is ViewState.ShowLoading -> {
            CircleLoaderUI()

        }

        is ViewState.ResultUiModel -> {
            val listBitCoins =
                (uiState as ViewState.ResultUiModel<Any?>)?.uiModel as List<Bitcoin>
            AllCoinsContent(listBitCoins)
        }

        is ViewState.ErrorUiModel -> {
            ErrorUI {

            }

        }
    }

    Spacer(modifier = Modifier.height(16.dp))
}


@Composable
fun AllCoinsContent(listBitCoins: List<Bitcoin>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(listBitCoins) { coin ->

                Card(
                    modifier = Modifier.background(
                        Color.Transparent,
                        shape = RoundedCornerShape(10.dp)
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color.Transparent
                            )
                            .padding(
                                10.dp
                            )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = coin.name,
                                    style = MaterialTheme.typography.labelLarge
                                )

                                Text(
                                    text = coin.symbol ?: "",
                                    style = MaterialTheme.typography.labelMedium,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            Text(
                                text = coin.priceUsd + " " + coin.currency,
                                style = MaterialTheme.typography.labelLarge,
                                color = Color.Red
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Spacer(modifier = Modifier.height(8.dp))
//            }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

