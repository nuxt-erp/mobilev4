package com.github.htdangkhoa.nexterp.domain.product


@Suppress("PropertyName")
class ProductParam constructor(val type: Int) {
    internal annotation class Type {
        companion object {
            const val GET_PRODUCT = 1
        }
    }

    var location_id: Int? = null
    var searchable: String = ""

    constructor(type: Int, location_id: Int?, searchable: String) : this(ProductParam.Type.GET_PRODUCT) {
        this.location_id = location_id
        this.searchable = searchable
    }

}
