package com.github.htdangkhoa.nexterp.data.remote.stockcount

import com.github.htdangkhoa.nexterp.data.model.ResponseModel
import com.google.gson.annotations.SerializedName

data class StockCountResponse(
    @SerializedName("status")
    var status: Boolean,

    @SerializedName("data")
    override val data: Array<StockCount>,

    @SerializedName("message")
    var message: String

) : ResponseModel<Array<StockCountResponse.StockCount>>() {
    data class StockCount(
        @SerializedName("id")
        var id: Int,

        @SerializedName("name")
        var name: String,

        @SerializedName("status_name")
        var status_name: String,

        @SerializedName("date")
        var date: String?,

        @SerializedName("brand_ids")
        var brand_ids: List<Long>?,

        @SerializedName("tag_ids")
        var tag_ids: List<Long>?,

        @SerializedName("bin_ids")
        var bin_ids: List<Long>?,

        @SerializedName("category_ids")
        var category_ids: List<Long>?,

        @SerializedName("stock_locator_ids")
        var stock_locator_ids: List<Long>?,
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StockCountResponse

        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }
}

