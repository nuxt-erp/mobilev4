package com.github.htdangkhoa.nexterp.data.remote.stockcount.stock_count_details

import com.google.gson.annotations.SerializedName

data class UpdateStockCountRequest(

    @SerializedName("list_products")
    var list_products: List<StockCountDetailResponse.StockCountDetail>
)
