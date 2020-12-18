package com.github.htdangkhoa.nexterp.data.remote.receiving.receiving

import com.github.htdangkhoa.nexterp.data.model.ResponseModel
import com.google.gson.annotations.SerializedName

data class ReceivingObjectResponse(
    @SerializedName("data")
    override val data: ReceivingResponse.Receiving?
) : ResponseModel<ReceivingResponse.Receiving?>()