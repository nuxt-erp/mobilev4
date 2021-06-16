package com.github.htdangkhoa.nexterp.data.repository.stockcount

import com.github.htdangkhoa.nexterp.base.BaseRepository
import com.github.htdangkhoa.nexterp.data.remote.pagination.PaginationObject
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving_details.ReceivingDetailsResponse
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stock_count_details.StockCountDetailResponse
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stock_count_details.UpdateStockCountRequest
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stockcount.NewStockCountRequest
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stockcount.StockCountResponse

interface StockCountRepository : BaseRepository {
    suspend fun getStockCount(nextPage: Int): Result<Pair<Array<StockCountResponse.StockCount?>, PaginationObject?>>
    suspend fun updateStockCount(
        id: Int,
        stockCountRequest: UpdateStockCountRequest
    ): Result<StockCountResponse.StockCount?>

    suspend fun newStockCount(
        stockCountRequest: NewStockCountRequest
    ): Result<StockCountResponse.StockCount?>

    suspend fun getStockCountDetails(id: Int, nextPage: Int): Result<Pair<Array<StockCountDetailResponse.StockCountDetail?>, PaginationObject?>>
    suspend fun finishStockCount(id: Int): Result<Array<StockCountResponse.StockCount?>>
    suspend fun voidStockCount(id: Int): Result<StockCountResponse.StockCount?>
    suspend fun deleteStockCountDetail(id: Int): Result<Array<StockCountDetailResponse.StockCountDetail?>>
}
