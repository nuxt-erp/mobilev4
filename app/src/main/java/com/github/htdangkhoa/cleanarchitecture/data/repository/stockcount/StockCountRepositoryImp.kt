package com.github.htdangkhoa.cleanarchitecture.data.repository.stockcount

import android.util.Log
import com.github.htdangkhoa.cleanarchitecture.base.BaseRepositoryImp
import com.github.htdangkhoa.cleanarchitecture.data.remote.stockcount.StockCountResponse
import com.github.htdangkhoa.cleanarchitecture.data.service.ApiService
import com.github.htdangkhoa.cleanarchitecture.extension.map
import retrofit2.HttpException

class StockCountRepositoryImp(
    apiService: ApiService
) : BaseRepositoryImp(apiService), StockCountRepository {

    override suspend fun getStockCount(): Result<Array<StockCountResponse.StockCount>> {
        return try {
            val res = apiService.getStockCount()

            Log.e("res->>>", res.toString())
            Result.map(res)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }
}
