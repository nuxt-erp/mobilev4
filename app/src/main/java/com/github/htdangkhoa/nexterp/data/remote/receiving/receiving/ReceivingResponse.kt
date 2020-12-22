package com.github.htdangkhoa.nexterp.data.remote.receiving.receiving

import android.os.Parcelable
import com.github.htdangkhoa.nexterp.data.model.ResponseModel
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ReceivingResponse(
    @SerializedName("status")
    var status: Boolean,

    @SerializedName("data")
    override val data: Array<Receiving?>,

    @SerializedName("message")
    var message: String

) : ResponseModel<Array<ReceivingResponse.Receiving?>>() {
    @Parcelize
    data class Receiving(
        @SerializedName("id")
        var id: Int,

        @SerializedName("name")
        var name: String,

        @SerializedName("po_number")
        var po_number: String,

        @SerializedName("invoice_number")
        var invoice_number: String,

        @SerializedName("supplier_name")
        var supplier_name: String,

        @SerializedName("status")
        var status: String,

        @SerializedName("created_at")
        var created_at: String
    ) : Parcelable

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ReceivingResponse

        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }
}

