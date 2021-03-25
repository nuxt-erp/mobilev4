package com.github.htdangkhoa.nexterp.data.remote.availability
import com.github.htdangkhoa.nexterp.data.model.ResponseModel
import com.google.gson.annotations.SerializedName

data class ProductAvailabilityObjectResponse(
    @SerializedName("data")
    override val data: ProductAvailabilityResponse.ProductAvailability
) : ResponseModel<ProductAvailabilityResponse.ProductAvailability>()