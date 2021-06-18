package com.github.htdangkhoa.nexterp.domain.locationbin
import com.github.htdangkhoa.nexterp.base.BaseUseCase
import com.github.htdangkhoa.nexterp.data.repository.locationbin.BinRepository

class BinUseCase(
    repository: BinRepository
) : BaseUseCase<BinRepository, BinParam>(repository) {
    override suspend fun buildUseCase(params: BinParam?): Result<*> {
        return when (params?.type) {
            BinParam.Type.GET_BINS -> repository.getBin(params.barcode, params.location_id, params.list, params.is_enabled, params.per_page)
            else -> throw UnsupportedOperationException("This request is not support in this case!")
        }
    }
}
