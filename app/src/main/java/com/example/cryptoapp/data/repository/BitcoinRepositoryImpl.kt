package com.example.cryptoapp.data.repository

import com.example.cryptoapp.data.NetworkRouter.invokeApi
import com.example.cryptoapp.data.datasource.remote.BitcoinApi
import com.example.cryptoapp.data.mapCoinResponseDtoToConDomainLayer
import com.example.cryptoapp.data.mapAllCoinsDtoToAllCoinsDomainLayerList
import com.example.cryptoapp.data.mapAllHistoryCoinsDtoToAllCoinsDomainLayerList
import com.example.cryptoapp.domain.model.Bitcoin
import com.example.cryptoapp.domain.repository.BitcoinRepository
import javax.inject.Inject

class BitcoinRepositoryImpl @Inject constructor(
    private val api: BitcoinApi,
    private val appPreference: AppPreference
) : BitcoinRepository {
    override suspend fun getBitcoinPrice(): Bitcoin {
        val selecCurrency = appPreference.getLastSelectedCurrency()
        val result = invokeApi { api.getBitcoinPrice(selecCurrency) }
        return mapCoinResponseDtoToConDomainLayer(result, currency = selecCurrency)
    }

    override suspend fun getAllCoinPrice(): List<Bitcoin> {
        val selecCurrency = appPreference.getLastSelectedCurrency()
        val result = invokeApi { api.getAllCoins(selecCurrency) }
        return mapAllCoinsDtoToAllCoinsDomainLayerList(result, currency = selecCurrency)
    }

    override suspend fun getHistoricalBitCoinData(): List<Bitcoin> {
        val result = invokeApi { api.getHistoricalBitCoinData() }
        return mapAllHistoryCoinsDtoToAllCoinsDomainLayerList(result)
    }

}