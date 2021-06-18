package com.github.htdangkhoa.nexterp.data.remote.stockcount.stockcount

import com.google.gson.annotations.SerializedName

data class NewStockCountRequest(
    @SerializedName("name")
    var name: String,
    @SerializedName("location_id")
    var location_id: Int,
    @SerializedName("tag_ids")
    var tag_ids: List<Long>,
    @SerializedName("stock_locator_ids")
    var stock_locator_ids: List<Long>,
    @SerializedName("bin_ids")
    var bin_ids: List<Long>,
    @SerializedName("category_ids")
    var category_ids: List<Long>,
    @SerializedName("brand_ids")
    var brand_ids: List<Long>,
)
