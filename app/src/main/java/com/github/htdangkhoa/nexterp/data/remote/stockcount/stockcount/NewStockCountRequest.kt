package com.github.htdangkhoa.nexterp.data.remote.stockcount.stockcount

import com.google.gson.annotations.SerializedName

data class NewStockCountRequest(
    @SerializedName("name")
    var name: String,
    @SerializedName("stock_count_filters")
    var stock_count_filters: HashMap<String, List<Long>>

)
