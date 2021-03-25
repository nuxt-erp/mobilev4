package com.github.htdangkhoa.nexterp.data.repository.availability

import com.github.htdangkhoa.nexterp.base.BaseRepository
import com.github.htdangkhoa.nexterp.data.remote.availability.NewAvailabilityRequest
import com.github.htdangkhoa.nexterp.data.remote.availability.ProductAvailabilityResponse

interface ProductAvailabilityRepository : BaseRepository {
    suspend fun getProductAvailability(searchable: String, bin_searchable: String, location_id: Int, brand_ids: List<Long>?, tag_ids: List<Long>?, bin_ids: List<Long>?, category_ids: List<Long>?, stock_locator_ids: List<Long>?): Result<Array<ProductAvailabilityResponse.ProductAvailability>>
    suspend fun newProductAvailability(
        newAvailabilityRequest: NewAvailabilityRequest
    ): Result<ProductAvailabilityResponse.ProductAvailability?>

}
