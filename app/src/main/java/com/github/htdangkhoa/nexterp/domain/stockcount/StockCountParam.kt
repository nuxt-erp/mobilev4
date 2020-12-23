package com.github.htdangkhoa.nexterp.domain.stockcount

import com.github.htdangkhoa.nexterp.data.remote.stockcount.stock_count_details.UpdateStockCountRequest


class StockCountParam constructor(val type: Int) {
    internal annotation class Type {
        companion object {

            const val GET_STOCK_COUNT = 1
            const val GET_STOCK_COUNT_DETAILS = 2
            const val UPDATE_STOCK_COUNT = 3
            const val VOID_STOCK_COUNT  = 4
            const val FINISH_STOCK_COUNT  = 5
        }
    }

    lateinit var stockCountRequest: UpdateStockCountRequest
    var id: Int = 0

    constructor(type: Int, id: Int) : this(type) {
        this.id = id
    }

    constructor(type: Int, id: Int, stockCountRequest: UpdateStockCountRequest) : this(StockCountParam.Type.UPDATE_STOCK_COUNT) {
        this.stockCountRequest = stockCountRequest
        this.id = id
    }

}