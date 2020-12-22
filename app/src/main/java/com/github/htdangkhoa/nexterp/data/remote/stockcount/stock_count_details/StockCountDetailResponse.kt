package com.github.htdangkhoa.nexterp.data.remote.stockcount.stock_count_details

import com.github.htdangkhoa.nexterp.data.model.ResponseModel
import com.google.gson.annotations.SerializedName

data class StockCountDetailResponse(
    @SerializedName("status")
    var status: Boolean,

    @SerializedName("data")
    override val data: Array<StockCountDetail>,

    @SerializedName("message")
    var message: String

) : ResponseModel<Array<StockCountDetailResponse.StockCountDetail>>() {
    data class StockCountDetail(
        @SerializedName("id")
        var id: Int?,

        @SerializedName("product_name")
        var product_name: String,

        @SerializedName("product_id")
        var product_id: Int,

        @SerializedName("location_id")
        var location_id: Int,

        @SerializedName("bin_name")
        var bin_name: String,

        @SerializedName("bin_id")
        var bin_id: Int?,

        @SerializedName("qty")
        var qty: Int
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StockCountDetailResponse

        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }
}

