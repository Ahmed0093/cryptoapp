package com.example.cryptoapp.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class CoinResponseApiModel(
    @SerializedName("beta_value")
    val betaValue: Double?,
    @SerializedName("first_data_at")
    val firstDataAt: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("last_updated")
    val lastUpdated: String?,
    @SerializedName("max_supply")
    val maxSupply: Long?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("quotes")
    val quotes: HashMap<String, CurrencyResponseApiModel>,
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("total_supply")
    val totalSupply: Long
)

@Keep
class CoinResponse : ArrayList<CoinResponseApiModel>()

@Keep
data class CurrencyResponseApiModel(
    @SerializedName("ath_date")
    val athDate: String,
    @SerializedName("ath_price")
    val athPrice: Double,
    @SerializedName("market_cap")
    val marketCap: Double,
    @SerializedName("market_cap_change_24h")
    val marketCapChange24h: Double,
    @SerializedName("percent_change_12h")
    val percentChange12h: Double,
    @SerializedName("percent_change_15m")
    val percentChange15m: Double,
    @SerializedName("percent_change_1h")
    val percentChange1h: Double,
    @SerializedName("percent_change_1y")
    val percentChange1y: Double,
    @SerializedName("percent_change_24h")
    val percentChange24h: Double,
    @SerializedName("percent_change_30d")
    val percentChange30d: Double,
    @SerializedName("percent_change_30m")
    val percentChange30m: Double,
    @SerializedName("percent_change_6h")
    val percentChange6h: Double,
    @SerializedName("percent_change_7d")
    val percentChange7d: Double,
    @SerializedName("percent_from_price_ath")
    val percentFromPriceAth: Double,
    @SerializedName("price")
    val price: Double,
    @SerializedName("volume_24h")
    val volume24h: Double,
    @SerializedName("volume_24h_change_24h")
    val volume24hChange24h: Double
)


@Keep
class HistoryResponse : ArrayList<HistoryResponseItem>()

@Keep
data class HistoryResponseItem(
    @SerializedName("market_cap")
    val marketCap: Double,
    @SerializedName("price")
    val price: Double,
    @SerializedName("timestamp")
    val timestamp: String,
    @SerializedName("volume_24h")
    val volume24h: Double
)