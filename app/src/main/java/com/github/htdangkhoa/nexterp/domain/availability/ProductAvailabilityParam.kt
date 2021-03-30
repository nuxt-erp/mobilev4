package com.github.htdangkhoa.nexterp.domain.availability
import com.github.htdangkhoa.nexterp.data.remote.availability.NewAvailabilityRequest

@Suppress("PropertyName")
class ProductAvailabilityParam constructor(val type: Int) {
    internal annotation class Type {
        companion object {
            const val GET_PRODUCT_AVAILABILITY = 1
            const val NEW_PRODUCT_AVAILABILITY = 2
            const val GET_AVAILABILITY = 3
        }
    }

    var product_name: String = ""
    var searchable: String = ""
    var bin_barcode: String? = null
    var bin_searchable: String = ""
    var location_id: Int = 0
    var brand_ids: List<Long>? = null
    var tag_ids: List<Long>? = null
    var bin_ids: List<Long>? = null
    var category_ids: List<Long>? = null
    var stock_locator_ids: List<Long>? = null
    
    lateinit var moveAvailabilityRequest: NewAvailabilityRequest

    constructor(type: Int,
                product_name: String,
                location_id: Int,
    ) : this(ProductAvailabilityParam.Type.GET_AVAILABILITY) {
        this.location_id = location_id
        this.product_name = product_name
    }

    constructor(type: Int,
                product_name: String,
                bin_barcode: String,
                location_id: Int,
    ) : this(ProductAvailabilityParam.Type.GET_AVAILABILITY) {
        this.location_id = location_id
        this.product_name = product_name
        this.bin_barcode = bin_barcode
    }
    constructor(type: Int,
                searchable: String,
                bin_searchable: String,
                location_id: Int,
                brand_ids: List<Long>?,
                tag_ids: List<Long>?,
                bin_ids: List<Long>?,
                category_ids: List<Long>?,
                stock_locator_ids: List<Long>?
    ) : this(ProductAvailabilityParam.Type.GET_PRODUCT_AVAILABILITY) {
        this.location_id = location_id
        this.searchable = searchable
        this.bin_searchable = bin_searchable
        this.brand_ids = brand_ids
        this.tag_ids = tag_ids
        this.bin_ids = bin_ids
        this.category_ids = category_ids
        this.stock_locator_ids = stock_locator_ids
    }

    constructor(type: Int, moveAvailabilityRequest: NewAvailabilityRequest) : this(ProductAvailabilityParam.Type.NEW_PRODUCT_AVAILABILITY) {
        this.moveAvailabilityRequest = moveAvailabilityRequest
    }

}
