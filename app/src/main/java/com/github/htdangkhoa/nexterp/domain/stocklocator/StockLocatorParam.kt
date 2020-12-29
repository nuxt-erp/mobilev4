package com.github.htdangkhoa.nexterp.domain.stocklocator


class StockLocatorParam constructor(val type: Int) {
    internal annotation class Type {
        companion object {

            const val GET_STOCK_LOCATOR = 1
        }

    }
    var list: Int = 0
    var is_enabled: Int = 0

    constructor(type: Int, list : Int, is_enabled: Int) : this(StockLocatorParam.Type.GET_STOCK_LOCATOR) {
        this.list = list
        this.is_enabled = is_enabled
    }
}
