package com.github.htdangkhoa.nexterp.ui.main.fragments.receiving

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving.ReceivingResponse
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving_details.ReceivingDetailsResponse
import com.github.htdangkhoa.nexterp.domain.auth.AuthParam
import com.github.htdangkhoa.nexterp.domain.auth.AuthUseCase
import com.github.htdangkhoa.nexterp.domain.receiving.ReceivingParam
import com.github.htdangkhoa.nexterp.domain.receiving.ReceivingUseCase
import com.github.htdangkhoa.nexterp.extension.liveDataOf
import com.github.htdangkhoa.nexterp.resource.Resource
import com.github.htdangkhoa.nexterp.ui.main.fragments.receiving.form.ReceivingFormFragment
import com.github.htdangkhoa.nexterp.ui.main.fragments.receiving.list.ReceivingListFragment

class ReceivingViewModel(
    private val receivingUseCase: ReceivingUseCase,
    private val authUseCase: AuthUseCase

) : ViewModel() {
    val resourceReceiving = liveDataOf<Resource<Array<ReceivingResponse.Receiving>>>()
    val resourceReceivingDetails = liveDataOf<Resource<Array<ReceivingDetailsResponse.ReceivingDetails>>>()
    val resourceLogout = liveDataOf<Resource<String>>()

    fun onReceivingClick(view : View, receiving: ReceivingResponse.Receiving) {

        val id = receiving.id
//        val action = ReceivingFormFragment
//        val action = SpecifyAmountFragmentDirections.confirmationAction(id)
//        view.findNavController().navigate()
    }

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

    fun getReceivingDetails(id: Int) {
        resourceReceivingDetails.postValue(Resource.loading())
        receivingUseCase.execute<Array<ReceivingDetailsResponse.ReceivingDetails>> (ReceivingParam(ReceivingParam.Type.GET_RECEIVING_DETAILS, id)) {
            onComplete {
                resourceReceivingDetails.postValue(Resource.success(it))
            }

            onError {
                resourceReceivingDetails.postValue(Resource.error(it))
                throw it

            }

            onCancel {
                resourceReceivingDetails.postValue(Resource.error(it))
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
