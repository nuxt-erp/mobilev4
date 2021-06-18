package com.github.htdangkhoa.nexterp.domain.stocklocator
import com.github.htdangkhoa.nexterp.base.BaseUseCase
import com.github.htdangkhoa.nexterp.data.repository.stocklocator.StockLocatorRepository

class StockLocatorUseCase(
    repository: StockLocatorRepository
) : BaseUseCase<StockLocatorRepository, StockLocatorParam>(repository) {
    override suspend fun buildUseCase(params: StockLocatorParam?): Result<*> {
        return when (params?.type) {
            StockLocatorParam.Type.GET_STOCK_LOCATOR -> repository.getStockLocator(params.list, params.is_enabled, params.per_page)
            else -> throw UnsupportedOperationException("This request is not support in this case!")
        }
    }
}
