package com.github.htdangkhoa.nexterp.data.repository.availability

import com.github.htdangkhoa.nexterp.base.BaseRepositoryImp
import com.github.htdangkhoa.nexterp.data.remote.availability.NewAvailabilityRequest
import com.github.htdangkhoa.nexterp.data.remote.availability.ProductAvailabilityResponse
import com.github.htdangkhoa.nexterp.data.service.ApiService
import com.github.htdangkhoa.nexterp.extension.map
import retrofit2.HttpException

class ProductAvailabilityRepositoryImp(
    apiService: ApiService
) : BaseRepositoryImp(apiService), ProductAvailabilityRepository {
    override suspend fun getProductAvailability(
        searchable: String,
        bin_searchable: String,
        location_id: Int,
        brand_ids: List<Long>?,
        tag_ids: List<Long>?,
        bin_ids: List<Long>?,
        category_ids: List<Long>?,
        stock_locator_ids: List<Long>?
    ): Result<Array<ProductAvailabilityResponse.ProductAvailability>> {
        return try {
            val res = apiService.getProductAvailabilities(
                searchable,
                bin_searchable,
                location_id,
                brand_ids,
                tag_ids,
                bin_ids,
                category_ids,
                stock_locator_ids)
            Result.map(res)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }

    override suspend fun newProductAvailability(newAvailabilityRequest: NewAvailabilityRequest): Result<ProductAvailabilityResponse.ProductAvailability?> {
        return try {
            val res = apiService.newAvailability(newAvailabilityRequest)
            Result.map(res)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}