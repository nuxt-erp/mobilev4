package com.github.htdangkhoa.nexterp.data.remote.stockadjustment.stockadjustment

import android.os.Parcelable
import com.github.htdangkhoa.nexterp.data.model.ResponseModel
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class StockAdjustmentResponse(
    @SerializedName("status")
    var status: Boolean,

    @SerializedName("data")
    override val data: Array<StockAdjustment?>,

    @SerializedName("message")
    var message: String

) : ResponseModel<Array<StockAdjustmentResponse.StockAdjustment?>>() {
    @Parcelize
    data class StockAdjustment(
        @SerializedName("id")
        var id: Int,

        @SerializedName("name")
        var name: String,

        @SerializedName("notes")
        var notes: String,

        @SerializedName("status")
        var status: String,

        @SerializedName("location_name")
        var location_name: String,

        @SerializedName("adjustment_type")
        var adjustment_type: String,

        @SerializedName("effective_date")
        var effective_date: String?
    ) : Parcelable

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StockAdjustmentResponse

        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }
}

