package com.github.htdangkhoa.nexterp.data.repository.stockcount

import com.github.htdangkhoa.nexterp.base.BaseRepository
import com.github.htdangkhoa.nexterp.data.remote.stockcount.StockCountResponse

interface StockCountRepository : BaseRepository {
    suspend fun getStockCount(): Result<Array<StockCountResponse.StockCount>>
}
