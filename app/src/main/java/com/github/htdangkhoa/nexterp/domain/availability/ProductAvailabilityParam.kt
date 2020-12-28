package com.github.htdangkhoa.nexterp.domain.availability


@Suppress("PropertyName")
class ProductAvailabilityParam constructor(val type: Int) {
    internal annotation class Type {
        companion object {
            const val GET_PRODUCT_AVAILABILITY = 1
        }
    }

    var searchable: String = ""
    var location_id: Int = 0
    var brand_ids: List<Long>? = null
    var tag_ids: List<Long>? = null
    var bin_ids: List<Long>? = null
    var category_ids: List<Long>? = null
    var stock_locator_ids: List<Long>? = null

    constructor(type: Int,
                searchable: String,
                location_id: Int,
                brand_ids: List<Long>?,
                tag_ids: List<Long>?,
                bin_ids: List<Long>?,
                category_ids: List<Long>?,
                stock_locator_ids: List<Long>?
    ) : this(ProductAvailabilityParam.Type.GET_PRODUCT_AVAILABILITY) {
        this.location_id = location_id
        this.searchable = searchable
        this.brand_ids = brand_ids
        this.tag_ids = tag_ids
        this.bin_ids = bin_ids
        this.category_ids = category_ids
        this.stock_locator_ids = stock_locator_ids
    }

}
