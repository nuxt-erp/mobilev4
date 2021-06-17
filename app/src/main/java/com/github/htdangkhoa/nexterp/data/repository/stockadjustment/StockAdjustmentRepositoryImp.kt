package com.github.htdangkhoa.nexterp.data.repository.stockadjustment

import com.github.htdangkhoa.nexterp.base.BaseRepositoryImp
import com.github.htdangkhoa.nexterp.data.remote.stockadjustment.stock_adjustment_details.StockAdjustmentDetailResponse
import com.github.htdangkhoa.nexterp.data.remote.stockadjustment.stock_adjustment_details.UpdateStockAdjustmentRequest
import com.github.htdangkhoa.nexterp.data.remote.stockadjustment.stockadjustment.NewStockAdjustmentRequest
import com.github.htdangkhoa.nexterp.data.remote.stockadjustment.stockadjustment.StockAdjustmentResponse


import com.github.htdangkhoa.nexterp.data.service.ApiService
import com.github.htdangkhoa.nexterp.extension.map
import retrofit2.HttpException

class StockAdjustmentRepositoryImp(
    apiService: ApiService
) : BaseRepositoryImp(apiService), StockAdjustmentRepository {

    override suspend fun updateStockAdjustment(
        id: Int,
        stockAdjustmentRequest: UpdateStockAdjustmentRequest
    ): Result<StockAdjustmentResponse.StockAdjustment?> {
        return try {
            val res = apiService.updateStockAdjustment(id, stockAdjustmentRequest)
            Result.map(res)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun newStockAdjustment(
        stockAdjustmentRequest: NewStockAdjustmentRequest
    ): Result<StockAdjustmentResponse.StockAdjustment?> {
        return try {
            val res = apiService.newStockAdjustment(stockAdjustmentRequest)
            Result.map(res)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getStockAdjustment(): Result<Array<StockAdjustmentResponse.StockAdjustment?>> {
        return try {
            val res = apiService.getStockAdjustment()
            Result.map(res)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }

    override suspend fun getStockAdjustmentDetails(id: Int): Result<Array<StockAdjustmentDetailResponse.StockAdjustmentDetail?>> {
        return try {
            val res = apiService.getStockAdjustmentDetails(id)
            Result.map(res)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }

    override suspend fun voidStockAdjustment(id: Int): Result<StockAdjustmentResponse.StockAdjustment?> {
        return try {
            val res = apiService.voidStockAdjustment(id)
            Result.map(res)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }
    override suspend fun deleteStockAdjustmentDetail(id: Int): Result<Array<StockAdjustmentDetailResponse.StockAdjustmentDetail?>> {
        return try {
            val res = apiService.deleteStockAdjustmentDetail(id)
            Result.map(res)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }
}
