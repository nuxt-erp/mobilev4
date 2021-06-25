package com.github.htdangkhoa.nexterp.data.remote.purchase

import android.os.Parcelable
import com.github.htdangkhoa.nexterp.data.model.ResponseModel
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class PurchaseDetailsResponse(
    @SerializedName("status")
    var status: Boolean,

    @SerializedName("data")
    override val data: Array<PurchaseDetails?>,

    @SerializedName("message")
    var message: String

) : ResponseModel<Array<PurchaseDetailsResponse.PurchaseDetails?>>() {
    @Parcelize
    data class PurchaseDetails(
        @SerializedName("id")
        var id: Int?,

        @SerializedName("product_id")
        var product_id: Int,

        @SerializedName("qty")
        var qty: Int,

    ) :Parcelable

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PurchaseDetailsResponse

        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }
}

