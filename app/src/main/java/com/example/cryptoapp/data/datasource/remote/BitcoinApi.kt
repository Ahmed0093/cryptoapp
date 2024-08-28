package com.example.cryptoapp.data.datasource.remote

import com.example.cryptoapp.data.model.CoinResponseApiModel
import com.example.cryptoapp.data.model.CoinResponse
import com.example.cryptoapp.data.model.HistoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BitcoinApi {
    @GET("tickers/btc-bitcoin")
    suspend fun getBitcoinPrice(@Query("quotes") currency: String = "USD"): Response<CoinResponseApiModel>

    @GET("tickers")
    suspend fun getAllCoins(@Query("quotes") currency: String = "USD"): Response<CoinResponse>

    @GET("tickers/btc-bitcoin/historical")
    suspend fun getHistoricalBitCoinData(//TODO make it dynamic as For Now input Static for start and interval
        @Query("start") start: String = "2024-06-01",
        @Query("interval") interval: String = "14d"
    ): Response<HistoryResponse>
}
