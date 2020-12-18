package com.github.htdangkhoa.nexterp.domain.product
import com.github.htdangkhoa.nexterp.base.BaseUseCase
import com.github.htdangkhoa.nexterp.data.repository.product.ProductRepository

class ProductUseCase(
    repository: ProductRepository
) : BaseUseCase<ProductRepository, ProductParam>(repository) {
    override suspend fun buildUseCase(params: ProductParam?): Result<*> {
        return when (params?.type) {
            ProductParam.Type.GET_PRODUCT -> repository.getProduct(params.searchable, params.location_id)
            else -> throw UnsupportedOperationException("This request is not support in this case!")
        }
    }
}
