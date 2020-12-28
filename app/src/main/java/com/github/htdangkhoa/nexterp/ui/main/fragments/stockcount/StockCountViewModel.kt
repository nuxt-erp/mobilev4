package com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.github.htdangkhoa.nexterp.data.remote.availability.ProductAvailabilityResponse
import com.github.htdangkhoa.nexterp.data.remote.bin.BinResponse
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving.ReceivingResponse
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving_details.ReceivingDetailsResponse
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stock_count_details.StockCountDetailResponse
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stockcount.StockCountResponse
import com.github.htdangkhoa.nexterp.domain.auth.AuthParam
import com.github.htdangkhoa.nexterp.domain.auth.AuthUseCase
import com.github.htdangkhoa.nexterp.domain.availability.ProductAvailabilityParam
import com.github.htdangkhoa.nexterp.domain.availability.ProductAvailabilityUseCase
import com.github.htdangkhoa.nexterp.domain.bin.BinParam
import com.github.htdangkhoa.nexterp.domain.bin.BinUseCase
import com.github.htdangkhoa.nexterp.domain.receiving.ReceivingParam
import com.github.htdangkhoa.nexterp.domain.stockcount.StockCountParam
import com.github.htdangkhoa.nexterp.domain.stockcount.StockCountUseCase
import com.github.htdangkhoa.nexterp.extension.liveDataOf
import com.github.htdangkhoa.nexterp.resource.Resource
import com.github.htdangkhoa.nexterp.ui.main.fragments.receiving.list.ReceivingListFragmentDirections
import com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount.list.StockCountListFragmentDirections

class StockCountViewModel(
    private val stockCountUseCase: StockCountUseCase,
    private val authUseCase: AuthUseCase,
    private val binUseCase: BinUseCase,
    private val availabilityUseCase: ProductAvailabilityUseCase

) : ViewModel() {
    val resourceStockCount = liveDataOf<Resource<Array<StockCountResponse.StockCount>>>()
    val resourceBins = liveDataOf<Resource<Array<BinResponse.Bin>>>()
    val resourceStockCountDetails = liveDataOf<Resource<Array<StockCountDetailResponse.StockCountDetail>>>()
    val resourceProductAvailability = liveDataOf<Resource<Array<ProductAvailabilityResponse.ProductAvailability>>>()
    val resourceLogout = liveDataOf<Resource<String>>()
    fun onStockCountClick(view : View, stockCount: StockCountResponse.StockCount) {
        val action = StockCountListFragmentDirections.actionNavStockCountToStockCountFormFragment(stockCount)
        view.findNavController().navigate(action)

    }
    fun getStockCount() {
        resourceStockCount.postValue(Resource.loading())

        stockCountUseCase.execute<Array<StockCountResponse.StockCount>> (StockCountParam(StockCountParam.Type.GET_STOCK_COUNT)) {
            onComplete {
                resourceStockCount.postValue(Resource.success(it))
            }

            onError {
                resourceStockCount.postValue(Resource.error(it))
                throw it

            }

            onCancel {
                resourceStockCount.postValue(Resource.error(it))
            }
        }
    }
    fun getStockCountDetails(id: Int) {
        resourceStockCountDetails.postValue(Resource.loading())
        stockCountUseCase.execute<Array<StockCountDetailResponse.StockCountDetail>> (
            StockCountParam(
                StockCountParam.Type.GET_STOCK_COUNT_DETAILS, id)
        ) {
            onComplete {
                resourceStockCountDetails.postValue(Resource.success(it))
            }

            onError {
                resourceStockCountDetails.postValue(Resource.error(it))
                throw it
            }

            onCancel {
                resourceStockCountDetails.postValue(Resource.error(it))
            }
        }
    }

    fun getBin(barcode: String,
               location_id: Int,
               list: Int,
               is_enabled: Int) {
        resourceBins.postValue(Resource.loading())
        binUseCase.execute<Array<BinResponse.Bin>> (
            BinParam(
                BinParam.Type.GET_BINS, location_id, barcode, list, is_enabled)
        ) {
            onComplete {
                resourceBins.postValue(Resource.success(it))
            }

            onError {
                resourceBins.postValue(Resource.error(it))
                throw it
            }

            onCancel {
                resourceBins.postValue(Resource.error(it))
            }
        }
    }

    fun getProductAvailability(
           searchable: String,
           location_id: Int,
           brand_ids: List<Long>? = null,
           tag_ids: List<Long>? = null,
           bin_ids: List<Long>? = null,
           category_ids: List<Long>? = null,
           stock_locator_ids: List<Long>? = null) {
        resourceProductAvailability.postValue(Resource.loading())
        availabilityUseCase.execute<Array<ProductAvailabilityResponse.ProductAvailability>> (
            ProductAvailabilityParam(
                ProductAvailabilityParam.Type.GET_PRODUCT_AVAILABILITY, searchable, location_id, brand_ids, tag_ids, bin_ids, category_ids, stock_locator_ids)
        )
        {
            onComplete {
                resourceProductAvailability.postValue(Resource.success(it))
            }

            onError {
                resourceProductAvailability.postValue(Resource.error(it))
                throw it
            }

            onCancel {
                resourceProductAvailability.postValue(Resource.error(it))
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
