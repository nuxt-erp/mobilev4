package com.github.htdangkhoa.nexterp.data.remote.stockcount.stockcount

import com.github.htdangkhoa.nexterp.data.model.ResponseModel
import com.google.gson.annotations.SerializedName

data class StockCountObjectResponse(
    @SerializedName("status")
    var status: Boolean,

    @SerializedName("data")
    override val data: StockCountResponse.StockCount,

    @SerializedName("message")
    var message: String

) : ResponseModel<StockCountResponse.StockCount>()
