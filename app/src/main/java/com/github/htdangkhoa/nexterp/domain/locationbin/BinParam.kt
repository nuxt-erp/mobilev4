package com.github.htdangkhoa.nexterp.domain.locationbin


class BinParam constructor(val type: Int) {
    internal annotation class Type {
        companion object {

            const val GET_BINS = 1
        }

    }
    var location_id: Int? = null
    var list: Int = 0
    var is_enabled: Int = 0
    var per_page: Int? = 20
    var barcode: String? = null

    constructor(type: Int, location_id: Int?, barcode: String?, list : Int, is_enabled: Int, per_page: Int?) : this(BinParam.Type.GET_BINS) {
        this.location_id = location_id
        this.barcode = barcode
        this.list = list
        this.is_enabled = is_enabled
        this.per_page = per_page
    }
}
