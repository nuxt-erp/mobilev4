package com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount

import androidx.lifecycle.ViewModel
import com.github.htdangkhoa.nexterp.data.remote.stockcount.StockCountResponse
import com.github.htdangkhoa.nexterp.domain.auth.AuthParam
import com.github.htdangkhoa.nexterp.domain.auth.AuthUseCase
import com.github.htdangkhoa.nexterp.domain.stockcount.StockCountParam
import com.github.htdangkhoa.nexterp.domain.stockcount.StockCountUseCase
import com.github.htdangkhoa.nexterp.extension.liveDataOf
import com.github.htdangkhoa.nexterp.resource.Resource

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
