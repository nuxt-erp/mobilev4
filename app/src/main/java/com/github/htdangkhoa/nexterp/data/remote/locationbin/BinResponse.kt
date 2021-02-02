package com.github.htdangkhoa.nexterp.data.remote.locationbin

import com.github.htdangkhoa.nexterp.data.model.ResponseModel
import com.google.gson.annotations.SerializedName

data class BinResponse(
    @SerializedName("status")
    var status: Boolean,

    @SerializedName("data")
    override val data: Array<Bin>,

    @SerializedName("message")
    var message: String

) : ResponseModel<Array<BinResponse.Bin>>() {
    data class Bin(
        @SerializedName("id")
        var id: Int,

        @SerializedName("name")
        var name: String,

        @SerializedName("barcode")
        var barcode: String,

        @SerializedName("location_id")
        var location_id: Int,

        @SerializedName("location_name")
        var location_name: String,

        @SerializedName("is_enabled")
        var is_enabled: Int,

        @SerializedName("created_at")
        var created_at: String,

        @SerializedName("updated_at")
        var updated_at: String
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BinResponse

        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }
}

