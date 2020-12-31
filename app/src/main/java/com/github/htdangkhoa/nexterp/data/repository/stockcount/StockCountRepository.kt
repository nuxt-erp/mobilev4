package com.github.htdangkhoa.nexterp.data.repository.stockcount

import com.github.htdangkhoa.nexterp.base.BaseRepository
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stock_count_details.StockCountDetailResponse
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stock_count_details.UpdateStockCountRequest
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stockcount.NewStockCountRequest
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stockcount.StockCountResponse

interface StockCountRepository : BaseRepository {
    suspend fun getStockCount(): Result<Array<StockCountResponse.StockCount?>>
    suspend fun updateStockCount(
        id: Int,
        stockCountRequest: UpdateStockCountRequest
    ): Result<StockCountResponse.StockCount?>

    suspend fun newStockCount(
        stockCountRequest: NewStockCountRequest
    ): Result<StockCountResponse.StockCount?>

    suspend fun getStockCountDetails(id: Int): Result<Array<StockCountDetailResponse.StockCountDetail>>
    suspend fun finishStockCount(id: Int): Result<StockCountResponse.StockCount?>
    suspend fun deleteStockCount(id: Int): Result<Array<StockCountResponse.StockCount?>>
}
