package com.github.htdangkhoa.nexterp.domain.availability
import com.github.htdangkhoa.nexterp.base.BaseUseCase
import com.github.htdangkhoa.nexterp.data.repository.availability.ProductAvailabilityRepository
import com.github.htdangkhoa.nexterp.data.repository.product.ProductRepository

class ProductAvailabilityUseCase(
    repository: ProductAvailabilityRepository
) : BaseUseCase<ProductAvailabilityRepository, ProductAvailabilityParam>(repository) {
    override suspend fun buildUseCase(params: ProductAvailabilityParam?): Result<*> {
        return when (params?.type) {
            ProductAvailabilityParam.Type.GET_PRODUCT_AVAILABILITY -> repository.getProductAvailability(params.searchable, params.location_id, params.brand_ids, params.tag_ids, params.bin_ids, params.category_ids, params.stock_locator_ids)
            else -> throw UnsupportedOperationException("This request is not support in this case!")
        }
    }
}
