package com.github.htdangkhoa.nexterp.ui.main.fragments.receiving

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.github.htdangkhoa.nexterp.data.remote.product.ProductResponse
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving.NewReceivingRequest
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving.ReceivingResponse
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving_details.ReceivingDetailsResponse
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving_details.UpdateReceivingRequest
import com.github.htdangkhoa.nexterp.domain.product.ProductParam
import com.github.htdangkhoa.nexterp.domain.product.ProductUseCase
import com.github.htdangkhoa.nexterp.domain.receiving.ReceivingParam
import com.github.htdangkhoa.nexterp.domain.receiving.ReceivingUseCase
import com.github.htdangkhoa.nexterp.extension.liveDataOf
import com.github.htdangkhoa.nexterp.resource.Resource
import com.github.htdangkhoa.nexterp.ui.main.fragments.receiving.list.ReceivingListFragmentDirections

class ReceivingViewModel(
    private val receivingUseCase: ReceivingUseCase,
    private val productUseCase: ProductUseCase
) : ViewModel() {
    val resourceProduct = liveDataOf<Resource<Array<ProductResponse.Product>>>()
    val resourceReceiving = liveDataOf<Resource<Array<ReceivingResponse.Receiving>>>()
    val resourceReceivingObject = liveDataOf<Resource<ReceivingResponse.Receiving>>()
    val resourceReceivingDetails = liveDataOf<Resource<Array<ReceivingDetailsResponse.ReceivingDetails?>>>()
    val resourceDeleteReceivingDetails = liveDataOf<Resource<Array<ReceivingDetailsResponse.ReceivingDetails?>>>()
    val resourceFinish = liveDataOf<Resource<ReceivingResponse.Receiving>>()
    val resourceVoid =  liveDataOf<Resource<Array<ReceivingResponse.Receiving?>>>()

    fun onReceivingClick(view : View, receiving: ReceivingResponse.Receiving) {
        val action = ReceivingListFragmentDirections.actionNavReceivingToReceivingFormFragment(receiving)
        view.findNavController().navigate(action)
    }

    fun getProduct(locationId: Int?, searchable: String) {
        resourceProduct.postValue(Resource.loading())
        productUseCase.execute<Array<ProductResponse.Product>> (ProductParam(ProductParam.Type.GET_PRODUCT, locationId, searchable)) {
            onComplete {
                resourceProduct.postValue(Resource.success(it))
            }

            onError {
                resourceProduct.postValue(Resource.error(it))
                throw it
            }

            onCancel {
                resourceProduct.postValue(Resource.error(it))
            }
        }
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

    fun updateReceiving(id: Int, locationId: Int, list_products: List<ReceivingDetailsResponse.ReceivingDetails?>) {
        val request = UpdateReceivingRequest(
            location_id = locationId,
            list_products = list_products
        )

        resourceReceivingObject.postValue(Resource.loading())

        receivingUseCase.execute<ReceivingResponse.Receiving>(ReceivingParam(ReceivingParam.Type.UPDATE_RECEIVING, id, request)) {
            onComplete {
                resourceReceivingObject.postValue(Resource.success(it))
            }

            onError {
                resourceReceivingObject.postValue(Resource.error(it))
            }

            onCancel {
                resourceReceivingObject.postValue(Resource.error(it))
            }
        }
    }

    fun newReceiving(locationId: Int, name: String, poNumber: String?, invoiceNumber: String?, trackingNumber: String?) {
        val request = NewReceivingRequest(
            location_id = locationId,
            name = name,
            poNumber = poNumber,
            invoiceNumber = invoiceNumber,
            trackingNumber = trackingNumber
        )

        resourceReceivingObject.postValue(Resource.loading())

        receivingUseCase.execute<ReceivingResponse.Receiving>(ReceivingParam(ReceivingParam.Type.NEW_RECEIVING, request)) {
            onComplete {
                resourceReceivingObject.postValue(Resource.success(it))
            }

            onError {
                resourceReceivingObject.postValue(Resource.error(it))
            }

            onCancel {
                resourceReceivingObject.postValue(Resource.error(it))
            }
        }
    }

    fun voidReceiving(id: Int) {
        resourceVoid.postValue(Resource.loading())

        receivingUseCase.execute<Array<ReceivingResponse.Receiving?>>(ReceivingParam(ReceivingParam.Type.VOID_RECEIVING, id)) {
            onComplete {
                resourceVoid.postValue(Resource.success(it))
            }

            onError {
                resourceVoid.postValue(Resource.error(it))
            }

            onCancel {
                resourceVoid.postValue(Resource.error(it))
            }
        }
    }

    fun deleteReceivingDetail(id: Int) {
        resourceDeleteReceivingDetails.postValue(Resource.loading())

        receivingUseCase.execute<Array<ReceivingDetailsResponse.ReceivingDetails?>>(ReceivingParam(ReceivingParam.Type.DELETE_RECEIVING_DETAIL, id)) {
            onComplete {
                resourceDeleteReceivingDetails.postValue(Resource.success(it))
            }

            onError {
                resourceDeleteReceivingDetails.postValue(Resource.error(it))
            }

            onCancel {
                resourceDeleteReceivingDetails.postValue(Resource.error(it))
            }
        }
    }

    fun finishReceiving(id: Int) {
        resourceFinish.postValue(Resource.loading())

        receivingUseCase.execute<ReceivingResponse.Receiving>(ReceivingParam(ReceivingParam.Type.FINISH_RECEIVING, id)) {
            onComplete {
                resourceFinish.postValue(Resource.success(it))
            }

            onError {
                resourceFinish.postValue(Resource.error(it))
            }

            onCancel {
                resourceFinish.postValue(Resource.error(it))
            }
        }
    }

    fun getReceivingDetails(id: Int) {
        resourceReceivingDetails.postValue(Resource.loading())
        receivingUseCase.execute<Array<ReceivingDetailsResponse.ReceivingDetails?>> (ReceivingParam(ReceivingParam.Type.GET_RECEIVING_DETAILS, id)) {
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
}
