package com.github.htdangkhoa.nexterp.domain.receiving
import com.github.htdangkhoa.nexterp.base.BaseUseCase
import com.github.htdangkhoa.nexterp.data.repository.receiving.ReceivingRepository

class ReceivingUseCase(
    repository: ReceivingRepository
) : BaseUseCase<ReceivingRepository, ReceivingParam>(repository) {
    override suspend fun buildUseCase(params: ReceivingParam?): Result<*> {
        return when (params?.type) {
            ReceivingParam.Type.GET_RECEIVING -> repository.getReceiving()
            ReceivingParam.Type.GET_RECEIVING_DETAILS -> repository.getReceivingDetails(params.id)
            else -> throw UnsupportedOperationException("This request is not support in this case!")
        }
    }
}
