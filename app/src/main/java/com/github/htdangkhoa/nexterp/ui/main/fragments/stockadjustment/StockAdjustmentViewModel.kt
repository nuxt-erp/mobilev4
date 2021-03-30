package com.github.htdangkhoa.nexterp.ui.main.fragments.stockadjustment

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.github.htdangkhoa.nexterp.data.remote.availability.AvailabilityResponse
import com.github.htdangkhoa.nexterp.data.remote.availability.NewAvailabilityRequest
import com.github.htdangkhoa.nexterp.data.remote.availability.ProductAvailabilityResponse
import com.github.htdangkhoa.nexterp.data.remote.locationbin.BinResponse
import com.github.htdangkhoa.nexterp.data.remote.brand.BrandResponse
import com.github.htdangkhoa.nexterp.data.remote.category.CategoryResponse
import com.github.htdangkhoa.nexterp.data.remote.stockadjustment.stock_adjustment_details.StockAdjustmentDetailResponse
import com.github.htdangkhoa.nexterp.data.remote.stockadjustment.stock_adjustment_details.UpdateStockAdjustmentRequest
import com.github.htdangkhoa.nexterp.data.remote.stockadjustment.stockadjustment.NewStockAdjustmentRequest
import com.github.htdangkhoa.nexterp.data.remote.stockadjustment.stockadjustment.StockAdjustmentResponse
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stock_count_details.StockCountDetailResponse
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stock_count_details.UpdateStockCountRequest
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stockcount.NewStockCountRequest
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stockcount.StockCountResponse
import com.github.htdangkhoa.nexterp.data.remote.stocklocator.StockLocatorResponse
import com.github.htdangkhoa.nexterp.data.remote.tag.TagResponse
import com.github.htdangkhoa.nexterp.domain.availability.ProductAvailabilityParam
import com.github.htdangkhoa.nexterp.domain.availability.ProductAvailabilityUseCase
import com.github.htdangkhoa.nexterp.domain.locationbin.BinParam
import com.github.htdangkhoa.nexterp.domain.locationbin.BinUseCase
import com.github.htdangkhoa.nexterp.domain.brand.BrandParam
import com.github.htdangkhoa.nexterp.domain.brand.BrandUseCase
import com.github.htdangkhoa.nexterp.domain.category.CategoryParam
import com.github.htdangkhoa.nexterp.domain.category.CategoryUseCase
import com.github.htdangkhoa.nexterp.domain.stockadjustment.StockAdjustmentParam
import com.github.htdangkhoa.nexterp.domain.stockadjustment.StockAdjustmentUseCase
import com.github.htdangkhoa.nexterp.domain.stockcount.StockCountParam
import com.github.htdangkhoa.nexterp.domain.stockcount.StockCountUseCase
import com.github.htdangkhoa.nexterp.domain.stocklocator.StockLocatorParam
import com.github.htdangkhoa.nexterp.domain.stocklocator.StockLocatorUseCase
import com.github.htdangkhoa.nexterp.domain.tag.TagParam
import com.github.htdangkhoa.nexterp.domain.tag.TagUseCase
import com.github.htdangkhoa.nexterp.extension.liveDataOf
import com.github.htdangkhoa.nexterp.resource.Resource
import com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount.list.StockCountListFragmentDirections

class StockAdjustmentViewModel(
    private val stockAdjustmentUseCase: StockAdjustmentUseCase,
    private val availabilityUseCase: ProductAvailabilityUseCase,
) : ViewModel() {
    val resourceStockAdjustment = liveDataOf<Resource<Array<StockAdjustmentResponse.StockAdjustment>>>()
    val resourceStockAdjustmentObject = liveDataOf<Resource<StockAdjustmentResponse.StockAdjustment>>()
    val resourceNewAvailabilityObject = liveDataOf<Resource<ProductAvailabilityResponse.ProductAvailability>>()
    val resourceVoid =  liveDataOf<Resource<Array<StockAdjustmentResponse.StockAdjustment?>>>()
    val resourceStockAdjustmentDetails = liveDataOf<Resource<Array<StockAdjustmentDetailResponse.StockAdjustmentDetail>>>()
    val resourceDeleteStockAdjustmentDetails = liveDataOf<Resource<Array<StockAdjustmentDetailResponse.StockAdjustmentDetail?>>>()
    val resourceAvailability = liveDataOf<Resource<Array<AvailabilityResponse.Availability>>>()

    fun getStockAdjustment() {
        resourceStockAdjustment.postValue(Resource.loading())

        stockAdjustmentUseCase.execute<Array<StockAdjustmentResponse.StockAdjustment>> (StockAdjustmentParam(StockAdjustmentParam.Type.GET_STOCK_ADJUSTMENT)) {
            onComplete {
                resourceStockAdjustment.postValue(Resource.success(it))
            }

            onError {
                resourceStockAdjustment.postValue(Resource.error(it))
                throw it

            }

            onCancel {
                resourceStockAdjustment.postValue(Resource.error(it))
            }
        }
    }
    fun getStockAdjustmentDetails(id: Int) {
        resourceStockAdjustmentDetails.postValue(Resource.loading())
        stockAdjustmentUseCase.execute<Array<StockAdjustmentDetailResponse.StockAdjustmentDetail>> (
            StockAdjustmentParam(
                StockAdjustmentParam.Type.GET_STOCK_ADJUSTMENT_DETAILS, id)
        ) {
            onComplete {
                resourceStockAdjustmentDetails.postValue(Resource.success(it))
            }

            onError {
                resourceStockAdjustmentDetails.postValue(Resource.error(it))
                throw it
            }

            onCancel {
                resourceStockAdjustmentDetails.postValue(Resource.error(it))
            }
        }
    }


    fun getAvailability(product_name: String, bin_barcode: String, locationId: Int) {
        resourceAvailability.postValue(Resource.loading())
        availabilityUseCase.execute<Array<AvailabilityResponse.Availability>> (
            ProductAvailabilityParam(ProductAvailabilityParam.Type.GET_AVAILABILITY, product_name, bin_barcode, locationId)
        ) {
            onComplete {
                resourceAvailability.postValue(Resource.success(it))
            }

            onError {
                resourceAvailability.postValue(Resource.error(it))
                throw it
            }

            onCancel {
                resourceAvailability.postValue(Resource.error(it))
            }
        }
    }

    fun newAvailability(bin_barcode: String, product_id: Int, locationId: Int) {
        val request = NewAvailabilityRequest(
            bin_barcode = bin_barcode,
            product_id = product_id,
            location_id = locationId
        )

        resourceNewAvailabilityObject.postValue(Resource.loading())

        availabilityUseCase.execute<ProductAvailabilityResponse.ProductAvailability>(ProductAvailabilityParam(ProductAvailabilityParam.Type.NEW_PRODUCT_AVAILABILITY, request)) {
            onComplete {
                resourceNewAvailabilityObject.postValue(Resource.success(it))
            }

            onError {
                resourceNewAvailabilityObject.postValue(Resource.error(it))
            }

            onCancel {
                resourceNewAvailabilityObject.postValue(Resource.error(it))
            }
        }
    }

    fun updateStockAdjustment(id: Int, locationId: Int, list_products: List<StockAdjustmentDetailResponse.StockAdjustmentDetail>) {
        val request = UpdateStockAdjustmentRequest(
            list_products = list_products,
            location_id = locationId
        )

        resourceStockAdjustmentObject.postValue(Resource.loading())

        stockAdjustmentUseCase.execute<StockAdjustmentResponse.StockAdjustment>(StockAdjustmentParam(StockAdjustmentParam.Type.UPDATE_STOCK_ADJUSTMENT, id, request)) {
            onComplete {
                resourceStockAdjustmentObject.postValue(Resource.success(it))
            }

            onError {
                resourceStockAdjustmentObject.postValue(Resource.error(it))
            }

            onCancel {
                resourceStockAdjustmentObject.postValue(Resource.error(it))
            }
        }
    }


    fun newStockAdjustment (name: String,  notes: String,  adjustment_type: String) {
        val request = NewStockAdjustmentRequest(name = name, notes = notes, adjustment_type = adjustment_type)

        resourceStockAdjustmentObject.postValue(Resource.loading())

        stockAdjustmentUseCase.execute<StockAdjustmentResponse.StockAdjustment>(StockAdjustmentParam(StockAdjustmentParam.Type.NEW_STOCK_ADJUSTMENT, request)) {
            onComplete {
                resourceStockAdjustmentObject.postValue(Resource.success(it))
            }

            onError {
                resourceStockAdjustmentObject.postValue(Resource.error(it))
            }

            onCancel {
                resourceStockAdjustmentObject.postValue(Resource.error(it))
            }
        }
    }

    fun voidStockAdjustment(id: Int) {
        resourceVoid.postValue(Resource.loading())

        stockAdjustmentUseCase.execute<Array<StockAdjustmentResponse.StockAdjustment?>>(StockAdjustmentParam(StockAdjustmentParam.Type.VOID_STOCK_ADJUSTMENT, id)) {
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

    fun deleteStockAdjustmentDetail(id: Int) {
        resourceDeleteStockAdjustmentDetails.postValue(Resource.loading())

        stockAdjustmentUseCase.execute<Array<StockAdjustmentDetailResponse.StockAdjustmentDetail?>>(
            StockAdjustmentParam(
                StockAdjustmentParam.Type.DELETE_STOCK_ADJUSTMENT_DETAIL, id)
        ) {
            onComplete {
                resourceDeleteStockAdjustmentDetails.postValue(Resource.success(it))
            }

            onError {
                resourceDeleteStockAdjustmentDetails.postValue(Resource.error(it))
            }

            onCancel {
                resourceDeleteStockAdjustmentDetails.postValue(Resource.error(it))
            }
        }
    }
}
