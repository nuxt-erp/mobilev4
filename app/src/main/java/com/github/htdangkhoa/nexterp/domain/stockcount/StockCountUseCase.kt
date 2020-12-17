package com.github.htdangkhoa.nexterp.domain.stockcount
import com.github.htdangkhoa.nexterp.base.BaseUseCase
import com.github.htdangkhoa.nexterp.data.repository.stockcount.StockCountRepository

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
