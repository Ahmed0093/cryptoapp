package com.example.cryptoapp.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.stringResource
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
import com.example.cryptoapp.ui.viewmodel.HistoryBitcoinViewModel
import io.jetchart.common.animation.fadeInAnimation
import io.jetchart.line.Line
import io.jetchart.line.LineChart
import io.jetchart.line.Point
import io.jetchart.line.renderer.line.GradientLineShader
import io.jetchart.line.renderer.line.SolidLineDrawer
import io.jetchart.line.renderer.point.FilledPointDrawer
import io.jetchart.line.renderer.xaxis.LineXAxisDrawer
import io.jetchart.line.renderer.yaxis.LineYAxisWithValueDrawer

@Composable
fun HistoryScreen(navController: NavController) {

    val historyBitcoinViewModel: HistoryBitcoinViewModel = hiltViewModel()
    val uiState by historyBitcoinViewModel.uiState.collectAsState()


    AppBar(
        title = stringResource(R.string.crypto_app),
        onLeadingIconClick = {
            navController.navigate(Screen.HOME.name) {
                popUpTo(Screen.AllCoins.name) {
                    inclusive = true

                }
            }
        },
        trailingIcon = {
            Text(
                text = stringResource(R.string.refresh),
                color = Color.Blue, modifier = Modifier.padding(top = 15.dp)
            )
        }, onTrailingIconClick = {
            historyBitcoinViewModel.fetchHistory()
        })
    Spacer(modifier = Modifier.height(16.dp))

    when (uiState) {

        is ViewState.ShowLoading -> {
            CircleLoaderUI()

        }

        is ViewState.ResultUiModel -> {
            val listBitCoins =
                (uiState as ViewState.ResultUiModel<Any?>)?.uiModel as List<Bitcoin>
            LineChartComposable(listBitCoins)
        }

        is ViewState.ErrorUiModel -> {
            ErrorUI {

            }

        }
    }

    Spacer(modifier = Modifier.height(16.dp))
}


@Composable
fun LineChartComposable(bitcoins: List<Bitcoin>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 140.dp),
        verticalArrangement = Arrangement.Top
    ) {
        LineChart(
            lines = listOf(
                Line(
                    points = points(bitcoins),
                    lineDrawer = SolidLineDrawer(thickness = 2.dp, color = Blue)
                ),
            ),
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .width(800.dp)
                .height(400.dp)
                .padding(30.dp),
            animation = fadeInAnimation(3000),
            pointDrawer = FilledPointDrawer(),
            xAxisDrawer = LineXAxisDrawer(),
            yAxisDrawer = LineYAxisWithValueDrawer(),
            horizontalOffsetPercentage = 1f,
            lineShader = GradientLineShader(listOf(Color.Transparent, Transparent))
        )
    }
}

@Composable
private fun points(bitCoins: List<Bitcoin>) =
    bitCoins.map { Point(it.priceUsd.toFloat(), "${it.timestamp}") }
