package com.mirosha.cryptocurrencyapp.domain.repository

import com.mirosha.cryptocurrencyapp.data.remote.dto.CoinDetailDto
import com.mirosha.cryptocurrencyapp.data.remote.dto.CoinDto

interface CoinRepository {

    suspend fun getCoins(): List<CoinDto>

    suspend fun getCoinById(coinId: String): CoinDetailDto
}