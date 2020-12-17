package com.github.htdangkhoa.cleanarchitecture.domain.stockcount


class StockCountParam constructor(val type: Int) {
    internal annotation class Type {
        companion object {

            const val GET_STOCK_COUNT = 1
        }
    }
}
