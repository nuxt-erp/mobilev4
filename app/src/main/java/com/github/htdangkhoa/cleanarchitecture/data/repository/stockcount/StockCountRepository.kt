package com.github.htdangkhoa.cleanarchitecture.data.repository.stockcount

import com.github.htdangkhoa.cleanarchitecture.base.BaseRepository
import com.github.htdangkhoa.cleanarchitecture.data.remote.stockcount.StockCountResponse

interface StockCountRepository : BaseRepository {
    suspend fun getStockCount(): Result<Array<StockCountResponse.StockCount>>
}
