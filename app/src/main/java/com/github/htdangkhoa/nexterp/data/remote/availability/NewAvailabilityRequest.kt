package com.github.htdangkhoa.nexterp.data.remote.availability
import com.google.gson.annotations.SerializedName

data class NewAvailabilityRequest(
    @SerializedName("bin_barcode")
    var bin_barcode: String,

    @SerializedName("product_id")
    var product_id: Int,

    @SerializedName("location_id")
    var location_id: Int

)
