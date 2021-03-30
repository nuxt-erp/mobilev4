package com.github.htdangkhoa.nexterp.data.remote.stockadjustment.stockadjustment

import com.github.htdangkhoa.nexterp.data.model.ResponseModel
import com.google.gson.annotations.SerializedName

class StockAdjustmentObjectResponse (
    @SerializedName("data")
    override val data: StockAdjustmentResponse.StockAdjustment?
) : ResponseModel<StockAdjustmentResponse.StockAdjustment?>()
