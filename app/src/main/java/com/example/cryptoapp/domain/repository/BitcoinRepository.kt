package com.example.cryptoapp.domain.repository

import com.example.cryptoapp.domain.model.Bitcoin


interface BitcoinRepository {
    suspend fun getBitcoinPrice(): Bitcoin
    suspend fun getAllCoinPrice(): List<Bitcoin>
    suspend fun getHistoricalBitCoinData(): List<Bitcoin>
}