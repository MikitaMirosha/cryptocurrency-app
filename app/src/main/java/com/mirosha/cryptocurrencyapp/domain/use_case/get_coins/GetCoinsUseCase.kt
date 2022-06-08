package com.mirosha.cryptocurrencyapp.domain.use_case.get_coins

import com.mirosha.cryptocurrencyapp.common.Resource
import com.mirosha.cryptocurrencyapp.common.error_constants.ErrorConstants.CONNECTION_EXCEPTION
import com.mirosha.cryptocurrencyapp.common.error_constants.ErrorConstants.UNEXPECTED_EXCEPTION
import com.mirosha.cryptocurrencyapp.data.remote.dto.toCoin
import com.mirosha.cryptocurrencyapp.domain.model.Coin
import com.mirosha.cryptocurrencyapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
) {

    operator fun invoke(): Flow<Resource<List<Coin>>> = flow {
        emit(Resource.Loading())
        val coins = repository.getCoins().map { it.toCoin() }
        try {
            emit(Resource.Success(coins))
        } catch (e: HttpException) {
            emit(Resource.Error(coins, e.localizedMessage ?: UNEXPECTED_EXCEPTION))
        } catch (e: IOException) {
            emit(Resource.Error(coins, e.localizedMessage ?: CONNECTION_EXCEPTION))
        }
    }
}