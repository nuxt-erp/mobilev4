package com.github.htdangkhoa.nexterp.domain.stockadjustment
import com.github.htdangkhoa.nexterp.base.BaseUseCase
import com.github.htdangkhoa.nexterp.data.repository.stockadjustment.StockAdjustmentRepository

class StockAdjustmentUseCase(
    repository: StockAdjustmentRepository
) : BaseUseCase<StockAdjustmentRepository, StockAdjustmentParam>(repository) {
    override suspend fun buildUseCase(params: StockAdjustmentParam?): Result<*> {
        return when (params?.type) {
            StockAdjustmentParam.Type.GET_STOCK_ADJUSTMENT -> repository.getStockAdjustment()
            StockAdjustmentParam.Type.GET_STOCK_ADJUSTMENT_DETAILS -> repository.getStockAdjustmentDetails(params.id)
            StockAdjustmentParam.Type.UPDATE_STOCK_ADJUSTMENT -> repository.updateStockAdjustment(params.id, params.stockAdjustmentRequest)
            StockAdjustmentParam.Type.NEW_STOCK_ADJUSTMENT -> repository.newStockAdjustment(params.newStockAdjustmentRequest)
            StockAdjustmentParam.Type.VOID_STOCK_ADJUSTMENT -> repository.deleteStockAdjustment(params.id)
            StockAdjustmentParam.Type.DELETE_STOCK_ADJUSTMENT_DETAIL -> repository.deleteStockAdjustmentDetail(params.id)

            else -> throw UnsupportedOperationException("This request is not support in this case!")
        }
    }
}
