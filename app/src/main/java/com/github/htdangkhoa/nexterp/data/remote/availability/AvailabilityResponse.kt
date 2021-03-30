package com.github.htdangkhoa.nexterp.data.remote.availability

import android.os.Parcelable
import com.github.htdangkhoa.nexterp.data.model.ResponseModel
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class AvailabilityResponse(
    @SerializedName("status")
    var status: Boolean,

    @SerializedName("data")
    override val data: Array<Availability>,

    @SerializedName("message")
    var message: String

) : ResponseModel<Array<AvailabilityResponse.Availability>>() {
    @Parcelize
    data class Availability(
        @SerializedName("id")
        var id: Int,

        @SerializedName("product_id")
        var product_id: Int,

        @SerializedName("product_name")
        var product_name: String,

        @SerializedName("sku")
        var product_sku: String,

        @SerializedName("product_barcode")
        var product_barcode: String?,

        @SerializedName("product_carton_barcode")
        var product_carton_barcode: String?,

        @SerializedName("brand_name")
        var product_brand: String,

        @SerializedName("category_name")
        var product_category: String,

        @SerializedName("location_id")
        var location_id: Int,

        @SerializedName("location_name")
        var location_name: String,

        @SerializedName("bin_id")
        var bin_id: Int?,

        @SerializedName("bin_name")
        var bin_name: String,

        @SerializedName("bin_barcode")
        var bin_barcode: String,

        @SerializedName("on_hand")
        var on_hand: Int,

        @SerializedName("on_order")
        var on_order: Int,

        @SerializedName("allocated")
        var allocated: Int,

        @SerializedName("qty")
        var qty: Int,

    ) : Parcelable

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AvailabilityResponse

        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }
}


