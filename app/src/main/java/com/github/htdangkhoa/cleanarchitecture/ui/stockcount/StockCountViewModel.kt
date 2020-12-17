package com.github.htdangkhoa.cleanarchitecture.ui.stockcount

import androidx.lifecycle.ViewModel
import com.github.htdangkhoa.cleanarchitecture.data.remote.stockcount.StockCountResponse
import com.github.htdangkhoa.cleanarchitecture.domain.auth.AuthParam
import com.github.htdangkhoa.cleanarchitecture.domain.auth.AuthUseCase
import com.github.htdangkhoa.cleanarchitecture.domain.stockcount.StockCountParam
import com.github.htdangkhoa.cleanarchitecture.domain.stockcount.StockCountUseCase
import com.github.htdangkhoa.cleanarchitecture.extension.liveDataOf
import com.github.htdangkhoa.cleanarchitecture.resource.Resource

class StockCountViewModel(
    private val stockCountUseCase: StockCountUseCase,
    private val authUseCase: AuthUseCase

) : ViewModel() {
    val resourceStockCount = liveDataOf<Resource<Array<StockCountResponse.StockCount>>>()
    val resourceLogout = liveDataOf<Resource<String>>()

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
