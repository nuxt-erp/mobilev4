package com.github.htdangkhoa.nexterp.data.repository.stocklocator

import com.github.htdangkhoa.nexterp.base.BaseRepositoryImp
import com.github.htdangkhoa.nexterp.data.remote.stocklocator.StockLocatorResponse
import com.github.htdangkhoa.nexterp.data.service.ApiService
import com.github.htdangkhoa.nexterp.extension.map
import retrofit2.HttpException

class StockLocatorRepositoryImp(
    apiService: ApiService
) : BaseRepositoryImp(apiService), StockLocatorRepository {

    override suspend fun getStockLocator(
        list: Int,
        is_enabled: Int
    ): Result<Array<StockLocatorResponse.StockLocator>> {
        return try {
            val res = apiService.getStockLocator(list, is_enabled)
            Result.map(res)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }
}
