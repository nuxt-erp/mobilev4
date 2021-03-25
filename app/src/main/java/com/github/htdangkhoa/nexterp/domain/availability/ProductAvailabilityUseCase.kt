package com.github.htdangkhoa.nexterp.domain.availability
import com.github.htdangkhoa.nexterp.base.BaseUseCase
import com.github.htdangkhoa.nexterp.data.repository.availability.ProductAvailabilityRepository

class ProductAvailabilityUseCase(
    repository: ProductAvailabilityRepository
) : BaseUseCase<ProductAvailabilityRepository, ProductAvailabilityParam>(repository) {
    override suspend fun buildUseCase(params: ProductAvailabilityParam?): Result<*> {
        return when (params?.type) {
            ProductAvailabilityParam.Type.GET_PRODUCT_AVAILABILITY -> repository.getProductAvailability(params.searchable, params.bin_searchable, params.location_id, params.brand_ids, params.tag_ids, params.bin_ids, params.category_ids, params.stock_locator_ids)
            ProductAvailabilityParam.Type.NEW_PRODUCT_AVAILABILITY -> repository.newProductAvailability(params.moveAvailabilityRequest)

            else -> throw UnsupportedOperationException("This request is not support in this case!")
        }
    }
}
