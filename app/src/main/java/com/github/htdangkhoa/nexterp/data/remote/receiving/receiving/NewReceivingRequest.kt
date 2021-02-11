package com.github.htdangkhoa.nexterp.data.remote.receiving.receiving

import com.google.gson.annotations.SerializedName

data class NewReceivingRequest(
    @SerializedName("name")
    var name: String,

    @SerializedName("po_number")
    var poNumber: String?,

    @SerializedName("invoice_number")
    var invoiceNumber: String?,

    @SerializedName("tracking_number")
    var trackingNumber: String?,

    @SerializedName("location_id")
    var location_id: Int
)
