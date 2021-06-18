package com.github.htdangkhoa.nexterp.data.repository.stocklocator

import com.github.htdangkhoa.nexterp.base.BaseRepository
import com.github.htdangkhoa.nexterp.data.remote.stocklocator.StockLocatorResponse

interface StockLocatorRepository : BaseRepository {
    suspend fun getStockLocator(list: Int, is_enabled: Int, per_page: Int?): Result<Array<StockLocatorResponse.StockLocator>>
}
