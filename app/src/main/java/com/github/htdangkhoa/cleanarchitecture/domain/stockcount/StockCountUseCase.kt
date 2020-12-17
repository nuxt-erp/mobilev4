package com.github.htdangkhoa.cleanarchitecture.domain.stockcount
import com.github.htdangkhoa.cleanarchitecture.base.BaseUseCase
import com.github.htdangkhoa.cleanarchitecture.data.repository.stockcount.StockCountRepository

class StockCountUseCase(
    repository: StockCountRepository
) : BaseUseCase<StockCountRepository, StockCountParam>(repository) {
    override suspend fun buildUseCase(params: StockCountParam?): Result<*> {
        return when (params?.type) {
            StockCountParam.Type.GET_STOCK_COUNT -> repository.getStockCount()
            else -> throw UnsupportedOperationException("This request is not support in this case!")
        }
    }
}
