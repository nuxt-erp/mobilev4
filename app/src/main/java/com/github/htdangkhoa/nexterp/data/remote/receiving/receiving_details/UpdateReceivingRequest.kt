
package com.github.htdangkhoa.nexterp.data.remote.receiving.receiving_details

import com.google.gson.annotations.SerializedName

data class UpdateReceivingRequest(
    @SerializedName("location_id")
    var location_id: Int,

    @SerializedName("list_products")
    var list_products: List<ReceivingDetailsResponse.ReceivingDetails>
)
