package com.github.htdangkhoa.nexterp.data.remote.availability

import android.os.Parcelable
import com.github.htdangkhoa.nexterp.data.model.ResponseModel
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ProductAvailabilityResponse(
    @SerializedName("status")
    var status: Boolean,

    @SerializedName("data")
    override val data: Array<ProductAvailability>,

    @SerializedName("message")
    var message: String

) : ResponseModel<Array<ProductAvailabilityResponse.ProductAvailability>>() {
    @Parcelize
    data class ProductAvailability(
        @SerializedName("product_id")
        var product_id: Int,

        @SerializedName("product_name")
        var product_name: String,

        @SerializedName("product_sku")
        var product_sku: String,

        @SerializedName("product_brand")
        var product_brand: String,

        @SerializedName("searchable")
        var searchable: String,

        @SerializedName("product_category")
        var product_category: String,

        @SerializedName("location_id")
        var location_id: Int,

        @SerializedName("location_name")
        var location_name: String,

        @SerializedName("bin_id")
        var bin_id: Int?,

        @SerializedName("bin_name")
        var bin_name: String,

        @SerializedName("on_hand")
        var on_hand: Int,

        @SerializedName("available")
        var available: Int,

        @SerializedName("qty")
        var qty: Int,

        @SerializedName("variance")
        var variance: Int,

        @SerializedName("notes")
        var notes: String
    ) : Parcelable

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProductAvailabilityResponse

        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }
}

