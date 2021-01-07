package com.github.htdangkhoa.nexterp.data.repository.stockcount

import com.github.htdangkhoa.nexterp.base.BaseRepositoryImp
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving_details.ReceivingDetailsResponse
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stock_count_details.StockCountDetailResponse
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stock_count_details.UpdateStockCountRequest
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stockcount.NewStockCountRequest
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stockcount.StockCountResponse

import com.github.htdangkhoa.nexterp.data.service.ApiService
import com.github.htdangkhoa.nexterp.extension.map
import retrofit2.HttpException

class StockCountRepositoryImp(
    apiService: ApiService
) : BaseRepositoryImp(apiService), StockCountRepository {

    override suspend fun updateStockCount(
        id: Int,
        stockCountRequest: UpdateStockCountRequest
    ): Result<StockCountResponse.StockCount?> {
        return try {
            val res = apiService.updateStockCount(id, stockCountRequest)
            Result.map(res)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun newStockCount(
        stockCountRequest: NewStockCountRequest
    ): Result<StockCountResponse.StockCount?> {
        return try {
            val res = apiService.newStockCount(stockCountRequest)
            Result.map(res)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getStockCount(): Result<Array<StockCountResponse.StockCount?>> {
        return try {
            val res = apiService.getStockCount()
            Result.map(res)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }

    override suspend fun getStockCountDetails(id: Int): Result<Array<StockCountDetailResponse.StockCountDetail?>> {
        return try {
            val res = apiService.getStockCountDetails(id)
            Result.map(res)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }

    override suspend fun finishStockCount(id: Int): Result<Array<StockCountResponse.StockCount?>> {
        return try {
            val res = apiService.finishStockCount(id)
            Result.map(res)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }

    override suspend fun deleteStockCount(id: Int): Result<Array<StockCountResponse.StockCount?>> {
        return try {
            val res = apiService.deleteStockCount(id)
            Result.map(res)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }
    override suspend fun deleteStockCountDetail(id: Int): Result<Array<StockCountDetailResponse.StockCountDetail?>> {
        return try {
            val res = apiService.deleteStockCountDetail(id)
            Result.map(res)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }
}
