package com.github.htdangkhoa.nexterp.data.repository.stockadjustment

import com.github.htdangkhoa.nexterp.base.BaseRepository
import com.github.htdangkhoa.nexterp.data.remote.stockadjustment.stock_adjustment_details.StockAdjustmentDetailResponse
import com.github.htdangkhoa.nexterp.data.remote.stockadjustment.stock_adjustment_details.UpdateStockAdjustmentRequest
import com.github.htdangkhoa.nexterp.data.remote.stockadjustment.stockadjustment.NewStockAdjustmentRequest
import com.github.htdangkhoa.nexterp.data.remote.stockadjustment.stockadjustment.StockAdjustmentResponse

interface StockAdjustmentRepository : BaseRepository {
    suspend fun getStockAdjustment(): Result<Array<StockAdjustmentResponse.StockAdjustment?>>
    suspend fun updateStockAdjustment(
        id: Int,
        stockAdjustmentRequest: UpdateStockAdjustmentRequest
    ): Result<StockAdjustmentResponse.StockAdjustment?>

    suspend fun newStockAdjustment(
        stockAdjustmentRequest: NewStockAdjustmentRequest
    ): Result<StockAdjustmentResponse.StockAdjustment?>

    suspend fun getStockAdjustmentDetails(id: Int): Result<Array<StockAdjustmentDetailResponse.StockAdjustmentDetail?>>
    suspend fun voidStockAdjustment(id: Int): Result<StockAdjustmentResponse.StockAdjustment?>
    suspend fun deleteStockAdjustmentDetail(id: Int): Result<Array<StockAdjustmentDetailResponse.StockAdjustmentDetail?>>
}