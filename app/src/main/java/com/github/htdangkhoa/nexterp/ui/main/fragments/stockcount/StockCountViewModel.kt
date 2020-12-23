package com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving.ReceivingResponse
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving_details.ReceivingDetailsResponse
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stock_count_details.StockCountDetailResponse
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stockcount.StockCountResponse
import com.github.htdangkhoa.nexterp.domain.auth.AuthParam
import com.github.htdangkhoa.nexterp.domain.auth.AuthUseCase
import com.github.htdangkhoa.nexterp.domain.receiving.ReceivingParam
import com.github.htdangkhoa.nexterp.domain.stockcount.StockCountParam
import com.github.htdangkhoa.nexterp.domain.stockcount.StockCountUseCase
import com.github.htdangkhoa.nexterp.extension.liveDataOf
import com.github.htdangkhoa.nexterp.resource.Resource
import com.github.htdangkhoa.nexterp.ui.main.fragments.receiving.list.ReceivingListFragmentDirections
import com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount.list.StockCountListFragmentDirections

class StockCountViewModel(
    private val stockCountUseCase: StockCountUseCase,
    private val authUseCase: AuthUseCase

) : ViewModel() {
    val resourceStockCount = liveDataOf<Resource<Array<StockCountResponse.StockCount>>>()
    val resourceStockCountDetails = liveDataOf<Resource<Array<StockCountDetailResponse.StockCountDetail>>>()
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
