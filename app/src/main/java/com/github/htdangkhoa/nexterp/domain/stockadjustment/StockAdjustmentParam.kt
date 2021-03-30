package com.github.htdangkhoa.nexterp.domain.stockadjustment

import com.github.htdangkhoa.nexterp.data.remote.stockadjustment.stock_adjustment_details.UpdateStockAdjustmentRequest
import com.github.htdangkhoa.nexterp.data.remote.stockadjustment.stockadjustment.NewStockAdjustmentRequest


class StockAdjustmentParam constructor(val type: Int) {
    internal annotation class Type {
        companion object {

            const val GET_STOCK_ADJUSTMENT = 1
            const val GET_STOCK_ADJUSTMENT_DETAILS = 2
            const val UPDATE_STOCK_ADJUSTMENT = 3
            const val VOID_STOCK_ADJUSTMENT  = 4
            const val NEW_STOCK_ADJUSTMENT = 5
            const val DELETE_STOCK_ADJUSTMENT_DETAIL = 6
        }
    }

    lateinit var stockAdjustmentRequest: UpdateStockAdjustmentRequest
    lateinit var newStockAdjustmentRequest: NewStockAdjustmentRequest
    var id: Int = 0

    constructor(type: Int, id: Int) : this(type) {
        this.id = id
    }

    constructor(type: Int, newStockAdjustmentRequest: NewStockAdjustmentRequest) : this(StockAdjustmentParam.Type.NEW_STOCK_ADJUSTMENT) {
        this.newStockAdjustmentRequest = newStockAdjustmentRequest
    }

    constructor(type: Int, id: Int, stockAdjustmentRequest: UpdateStockAdjustmentRequest) : this(StockAdjustmentParam.Type.UPDATE_STOCK_ADJUSTMENT) {
        this.stockAdjustmentRequest = stockAdjustmentRequest
        this.id = id
    }

}