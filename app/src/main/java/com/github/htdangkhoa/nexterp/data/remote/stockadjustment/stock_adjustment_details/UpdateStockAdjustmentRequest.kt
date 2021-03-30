package com.github.htdangkhoa.nexterp.data.remote.stockadjustment.stock_adjustment_details

import com.google.gson.annotations.SerializedName

data class UpdateStockAdjustmentRequest(

    @SerializedName("list_products")
    var list_products: List<StockAdjustmentDetailResponse.StockAdjustmentDetail>,

    @SerializedName("location_id")
    var location_id: Int
)
