package com.github.htdangkhoa.nexterp.data.remote.stockadjustment.stockadjustment
import com.google.gson.annotations.SerializedName

data class NewStockAdjustmentRequest (
    @SerializedName("name")
    var name: String,

    @SerializedName("notes")
    var notes: String,

    @SerializedName("adjustment_type")
    var adjustment_type: String
)