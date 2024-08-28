package com.example.cryptoapp.domain.useCase

import com.example.cryptoapp.domain.model.Bitcoin
import com.example.cryptoapp.domain.repository.BitcoinRepository
import javax.inject.Inject

class GetBitcoinPriceUseCase @Inject constructor(private val repository: BitcoinRepository) {
    suspend operator fun invoke(): Bitcoin {
        return repository.getBitcoinPrice()
    }
}