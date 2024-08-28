package com.example.cryptoapp.data

import com.example.cryptoapp.data.model.CoinResponse
import com.example.cryptoapp.data.model.CoinResponseApiModel
import com.example.cryptoapp.data.model.HistoryResponse
import com.example.cryptoapp.domain.convertToDateString
import com.example.cryptoapp.domain.convertToDateTimeString
import com.example.cryptoapp.domain.model.Bitcoin
import java.util.ArrayList
import java.util.Date

fun mapCoinResponseDtoToConDomainLayer(result: CoinResponseApiModel, currency: String = "USD"): Bitcoin {
    return Bitcoin(
        priceUsd = result.quotes[currency]?.price.toString(),
        timestamp = convertToDateTimeString(result.lastUpdated ?: Date().toString()),
        name = result.name ?: "",
        symbol = result.symbol,
        currency = currency
    )
}

fun mapAllCoinsDtoToAllCoinsDomainLayerList(
    result: CoinResponse, currency: String = "USD"
): List<Bitcoin> {
    val bitcoinArrayList: ArrayList<Bitcoin> = arrayListOf<Bitcoin>()
    result.forEach {
        bitcoinArrayList.add(mapCoinResponseDtoToConDomainLayer(it, currency))
    }
    return bitcoinArrayList
}
fun mapAllHistoryCoinsDtoToAllCoinsDomainLayerList(
    result: HistoryResponse
): List<Bitcoin> {
    val bitcoinArrayList: ArrayList<Bitcoin> = arrayListOf<Bitcoin>()
    result.forEach {
        bitcoinArrayList.add(
            Bitcoin(
                priceUsd = it.price.toString(),
                timestamp = convertToDateString(it.timestamp),
                symbol = "",
                name = "",
                currency = ""
            )
        )
    }
    return bitcoinArrayList
}