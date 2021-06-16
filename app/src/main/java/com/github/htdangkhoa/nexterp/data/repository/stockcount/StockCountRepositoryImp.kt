package com.github.htdangkhoa.nexterp.data.repository.stockcount

import com.github.htdangkhoa.nexterp.base.BaseRepositoryImp
import com.github.htdangkhoa.nexterp.data.remote.pagination.PaginationObject
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

    override suspend fun getStockCount(nextPage: Int): Result<Pair<Array<StockCountResponse.StockCount?>, PaginationObject?>> {
        return try {
            val res = apiService.getStockCount(nextPage)
            Result.success(Pair(res.data, res.pagination))
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }

    override suspend fun getStockCountDetails(id: Int, nextPage: Int): Result<Pair<Array<StockCountDetailResponse.StockCountDetail?>, PaginationObject?>> {
        return try {
            val res = apiService.getStockCountDetails(id, nextPage)
            Result.success(Pair(res.data, res.pagination))
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

    override suspend fun voidStockCount(id: Int): Result<StockCountResponse.StockCount?> {
        return try {
            val res = apiService.voidStockCount(id)
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
