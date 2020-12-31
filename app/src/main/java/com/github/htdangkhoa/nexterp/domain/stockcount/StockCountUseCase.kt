package com.github.htdangkhoa.nexterp.domain.stockcount
import com.github.htdangkhoa.nexterp.base.BaseUseCase
import com.github.htdangkhoa.nexterp.data.repository.stockcount.StockCountRepository

class StockCountUseCase(
    repository: StockCountRepository
) : BaseUseCase<StockCountRepository, StockCountParam>(repository) {
    override suspend fun buildUseCase(params: StockCountParam?): Result<*> {
        return when (params?.type) {
            StockCountParam.Type.GET_STOCK_COUNT -> repository.getStockCount()
            StockCountParam.Type.GET_STOCK_COUNT_DETAILS -> repository.getStockCountDetails(params.id)
            StockCountParam.Type.UPDATE_STOCK_COUNT -> repository.updateStockCount(params.id, params.stockCountRequest)
            StockCountParam.Type.NEW_STOCK_COUNT -> repository.newStockCount(params.newStockCountRequest)
            StockCountParam.Type.VOID_STOCK_COUNT -> repository.deleteStockCount(params.id)
            StockCountParam.Type.FINISH_STOCK_COUNT -> repository.finishStockCount(params.id)

            else -> throw UnsupportedOperationException("This request is not support in this case!")
        }
    }
}
