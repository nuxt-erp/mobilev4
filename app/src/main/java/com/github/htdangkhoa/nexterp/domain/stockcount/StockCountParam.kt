package com.github.htdangkhoa.nexterp.domain.stockcount


class StockCountParam constructor(val type: Int) {
    internal annotation class Type {
        companion object {

            const val GET_STOCK_COUNT = 1
        }
    }
}
