package com.github.htdangkhoa.nexterp.domain.brand
import com.github.htdangkhoa.nexterp.base.BaseUseCase
import com.github.htdangkhoa.nexterp.data.repository.brand.BrandRepository

class BrandUseCase(
    repository: BrandRepository
) : BaseUseCase<BrandRepository, BrandParam>(repository) {
    override suspend fun buildUseCase(params: BrandParam?): Result<*> {
        return when (params?.type) {
            BrandParam.Type.GET_BRANDS -> repository.getBrand(params.list, params.is_enabled)
            else -> throw UnsupportedOperationException("This request is not support in this case!")
        }
    }
}
