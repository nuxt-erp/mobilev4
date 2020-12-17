package com.github.htdangkhoa.nexterp.data.remote.receiving.receiving_details

import com.github.htdangkhoa.nexterp.data.model.ResponseModel
import com.google.gson.annotations.SerializedName

data class ReceivingDetailsResponse(
    @SerializedName("status")
    var status: Boolean,

    @SerializedName("data")
    override val data: Array<ReceivingDetails>,

    @SerializedName("message")
    var message: String

) : ResponseModel<Array<ReceivingDetailsResponse.ReceivingDetails>>() {
    data class ReceivingDetails(
        @SerializedName("id")
        var id: Int?,

        @SerializedName("product_id")
        var product_id: Int,

        @SerializedName("product_name")
        var product_name: String,

        @SerializedName("searchable")
        var searchable: String,

        @SerializedName("qty_allocated")
        var qty_allocated: Int,

        @SerializedName("qty_received")
        var qty_received: Int
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ReceivingDetailsResponse

        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }
}

