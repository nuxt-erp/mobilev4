package com.github.htdangkhoa.cleanarchitecture.domain.receiving
import com.github.htdangkhoa.cleanarchitecture.base.BaseUseCase
import com.github.htdangkhoa.cleanarchitecture.data.repository.receiving.ReceivingRepository

class ReceivingUseCase(
    repository: ReceivingRepository
) : BaseUseCase<ReceivingRepository, ReceivingParam>(repository) {
    override suspend fun buildUseCase(params: ReceivingParam?): Result<*> {
        return when (params?.type) {
            ReceivingParam.Type.GET_RECEIVING -> repository.getReceiving()
            else -> throw UnsupportedOperationException("This request is not support in this case!")
        }
    }
}
