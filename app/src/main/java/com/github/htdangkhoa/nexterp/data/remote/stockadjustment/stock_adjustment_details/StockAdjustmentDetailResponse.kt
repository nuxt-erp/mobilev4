package com.github.htdangkhoa.nexterp.data.remote.stockadjustment.stock_adjustment_details
import android.os.Parcelable
import com.github.htdangkhoa.nexterp.data.model.ResponseModel
import com.github.htdangkhoa.nexterp.data.remote.pagination.PaginationObject
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class StockAdjustmentDetailResponse(
    @SerializedName("status")
    var status: Boolean,

    @SerializedName("data")
    override val data: Array<StockAdjustmentDetail?>,

    @SerializedName("message")
    var message: String

) : ResponseModel<Array<StockAdjustmentDetailResponse.StockAdjustmentDetail?>>() {
    @Parcelize
    data class StockAdjustmentDetail(
        @SerializedName("id")
        var id: Int?,

        @SerializedName("product_id")
        var product_id: Int,

        @SerializedName("product_full_name")
        var product_full_name: String?,

        @SerializedName("searchable")
        var searchable: String,

        @SerializedName("product_sku")
        var product_sku: String?,

        @SerializedName("product_barcode")
        var product_barcode: String?,

        @SerializedName("product_carton_barcode")
        var product_carton_barcode: String?,

        @SerializedName("product_carton_qty")
        var product_carton_qty: Int?,

        @SerializedName("location_id")
        var location_id: Int,

        @SerializedName("bin_id")
        var bin_id: Int?,

        @SerializedName("bin_name")
        var bin_name: String?,

        @SerializedName("bin_searchable")
        var bin_searchable: String?,

        @SerializedName("adjustment_type")
        var adjustment_type: String?,

        @SerializedName("available_bin_barcodes")
        var available_bin_barcodes: String?,

        @SerializedName("status")
        var status: String?,

        @SerializedName("qty")
        var qty: Int,

        @SerializedName("variance")
        var variance: Int,

        @SerializedName("on_hand")
        var on_hand: Int

    ) : Parcelable

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StockAdjustmentDetailResponse

        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }
}

