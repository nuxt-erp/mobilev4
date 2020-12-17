package com.github.htdangkhoa.cleanarchitecture.ui.receiving

import androidx.lifecycle.ViewModel
import com.github.htdangkhoa.cleanarchitecture.data.remote.receiving.ReceivingResponse
import com.github.htdangkhoa.cleanarchitecture.domain.auth.AuthParam
import com.github.htdangkhoa.cleanarchitecture.domain.auth.AuthUseCase
import com.github.htdangkhoa.cleanarchitecture.domain.receiving.ReceivingParam
import com.github.htdangkhoa.cleanarchitecture.domain.receiving.ReceivingUseCase
import com.github.htdangkhoa.cleanarchitecture.extension.liveDataOf
import com.github.htdangkhoa.cleanarchitecture.resource.Resource

class ReceivingViewModel(
    private val receivingUseCase: ReceivingUseCase,
    private val authUseCase: AuthUseCase

) : ViewModel() {
    val resourceReceiving = liveDataOf<Resource<Array<ReceivingResponse.Receiving>>>()
    val resourceLogout = liveDataOf<Resource<String>>()


    fun getReceiving() {
        resourceReceiving.postValue(Resource.loading())

        receivingUseCase.execute<Array<ReceivingResponse.Receiving>> (ReceivingParam(ReceivingParam.Type.GET_RECEIVING)) {
            onComplete {
                resourceReceiving.postValue(Resource.success(it))
            }

            onError {
                resourceReceiving.postValue(Resource.error(it))
                throw it

            }

            onCancel {
                resourceReceiving.postValue(Resource.error(it))
            }
        }
    }
    fun logout() {
        resourceLogout.postValue(Resource.loading())

        authUseCase.execute<String>(AuthParam(AuthParam.Type.LOGOUT)) {
            onComplete {
                resourceLogout.postValue(Resource.success(it))
            }

            onError {
                resourceLogout.postValue(Resource.error(it))
            }

            onCancel {
                resourceLogout.postValue(Resource.error(it))
            }
        }
    }
}
