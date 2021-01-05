package com.github.htdangkhoa.nexterp.domain.stockcount

import com.github.htdangkhoa.nexterp.data.remote.stockcount.stock_count_details.UpdateStockCountRequest
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stockcount.NewStockCountRequest


class StockCountParam constructor(val type: Int) {
    internal annotation class Type {
        companion object {

            const val GET_STOCK_COUNT = 1
            const val GET_STOCK_COUNT_DETAILS = 2
            const val UPDATE_STOCK_COUNT = 3
            const val VOID_STOCK_COUNT  = 4
            const val FINISH_STOCK_COUNT  = 5
            const val NEW_STOCK_COUNT = 6
            const val DELETE_STOCK_COUNT_DETAIL = 7


        }
    }

    lateinit var stockCountRequest: UpdateStockCountRequest
    lateinit var newStockCountRequest: NewStockCountRequest
    var id: Int = 0

    constructor(type: Int, id: Int) : this(type) {
        this.id = id
    }

    constructor(type: Int, newStockCountRequest: NewStockCountRequest) : this(StockCountParam.Type.NEW_STOCK_COUNT) {
        this.newStockCountRequest = newStockCountRequest
    }

    constructor(type: Int, id: Int, stockCountRequest: UpdateStockCountRequest) : this(StockCountParam.Type.UPDATE_STOCK_COUNT) {
        this.stockCountRequest = stockCountRequest
        this.id = id
    }

}