package com.mirosha.cryptocurrencyapp.domain.use_case.get_coin

import com.mirosha.cryptocurrencyapp.common.Resource
import com.mirosha.cryptocurrencyapp.common.error_constants.ErrorConstants.CONNECTION_EXCEPTION
import com.mirosha.cryptocurrencyapp.common.error_constants.ErrorConstants.UNEXPECTED_EXCEPTION
import com.mirosha.cryptocurrencyapp.data.remote.dto.toCoinDetail
import com.mirosha.cryptocurrencyapp.domain.model.CoinDetail
import com.mirosha.cryptocurrencyapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    private val repository: CoinRepository
) {

    operator fun invoke(coinId: String): Flow<Resource<CoinDetail>> = flow {
        emit(Resource.Loading())
        val coin = repository.getCoinById(coinId).toCoinDetail()
        try {
            emit(Resource.Success(coin))
        } catch (e: HttpException) {
            emit(Resource.Error(coin, e.localizedMessage ?: UNEXPECTED_EXCEPTION))
        } catch (e: IOException) {
            emit(Resource.Error(coin, e.localizedMessage ?: CONNECTION_EXCEPTION))
        }
    }
}