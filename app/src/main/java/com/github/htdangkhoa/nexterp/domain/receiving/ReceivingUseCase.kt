package com.github.htdangkhoa.nexterp.domain.receiving
import com.github.htdangkhoa.nexterp.base.BaseUseCase
import com.github.htdangkhoa.nexterp.data.repository.receiving.ReceivingRepository
import com.github.htdangkhoa.nexterp.domain.auth.AuthParam

class ReceivingUseCase(
    repository: ReceivingRepository
) : BaseUseCase<ReceivingRepository, ReceivingParam>(repository) {
    override suspend fun buildUseCase(params: ReceivingParam?): Result<*> {
        return when (params?.type) {
            ReceivingParam.Type.GET_RECEIVING -> repository.getReceiving()
            ReceivingParam.Type.GET_RECEIVING_DETAILS -> repository.getReceivingDetails(params.id)
            ReceivingParam.Type.UPDATE_RECEIVING -> repository.updateReceiving(params.id, params.receivingRequest)
            ReceivingParam.Type.NEW_RECEIVING -> repository.newReceiving(params.newReceivingRequest)
            ReceivingParam.Type.VOID_RECEIVING -> repository.deleteReceiving(params.id)
            ReceivingParam.Type.FINISH_RECEIVING -> repository.finishReceiving(params.id)

            else -> throw UnsupportedOperationException("This request is not support in this case!")
        }
    }
}
