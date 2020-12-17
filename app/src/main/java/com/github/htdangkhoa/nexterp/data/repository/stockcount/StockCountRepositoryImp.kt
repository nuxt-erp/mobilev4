package com.github.htdangkhoa.nexterp.data.repository.stockcount

import android.util.Log
import com.github.htdangkhoa.nexterp.base.BaseRepositoryImp
import com.github.htdangkhoa.nexterp.data.remote.stockcount.StockCountResponse
import com.github.htdangkhoa.nexterp.data.service.ApiService
import com.github.htdangkhoa.nexterp.extension.map
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
