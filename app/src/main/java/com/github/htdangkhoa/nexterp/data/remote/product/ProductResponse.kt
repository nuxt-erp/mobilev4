package com.github.htdangkhoa.nexterp.data.remote.product

import android.os.Parcelable
import com.github.htdangkhoa.nexterp.data.model.ResponseModel
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ProductResponse(
    @SerializedName("status")
    var status: Boolean,

    @SerializedName("data")
    override val data: Array<Product>,

    @SerializedName("message")
    var message: String

) : ResponseModel<Array<ProductResponse.Product>>() {
    @Parcelize
    data class Product(
        @SerializedName("id")
        var id: Int,

        @SerializedName("name")
        var name: String?,

        @SerializedName("display_name")
        var product_full_name: String?,

        @SerializedName("sku")
        var sku: String,

        @SerializedName("brand")
        var brand: String,

        @SerializedName("barcode")
        var barcode: String?,

        @SerializedName("searchable")
        var searchable: String,

        @SerializedName("carton_barcode")
        var carton_barcode: String?,

        @SerializedName("carton_qty")
        var carton_qty: Int?,

        @SerializedName("category")
        var category: String,

        @SerializedName("location_id")
        var location_id: Int,

        @SerializedName("location_name")
        var location_name: String
    ) : Parcelable

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProductResponse

        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }
}

