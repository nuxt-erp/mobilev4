package com.github.htdangkhoa.nexterp.domain.brand


class BrandParam constructor(val type: Int) {
    internal annotation class Type {
        companion object {

            const val GET_BRANDS = 1
        }

    }
    var list: Int = 0
    var is_enabled: Int = 0
    var per_page: Int? = 20

    constructor(type: Int, list : Int, is_enabled: Int, per_page: Int?) : this(BrandParam.Type.GET_BRANDS) {
        this.list = list
        this.is_enabled = is_enabled
        this.per_page = per_page
    }
}
