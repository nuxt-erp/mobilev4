package com.github.htdangkhoa.nexterp.ui.main.fragments.availability

import androidx.lifecycle.ViewModel
import com.github.htdangkhoa.nexterp.data.remote.availability.AvailabilityResponse
import com.github.htdangkhoa.nexterp.domain.availability.ProductAvailabilityParam
import com.github.htdangkhoa.nexterp.domain.availability.ProductAvailabilityUseCase
import com.github.htdangkhoa.nexterp.extension.liveDataOf
import com.github.htdangkhoa.nexterp.resource.Resource

class AvailabilityViewModel(
    private val availabilityUseCase: ProductAvailabilityUseCase
) : ViewModel() {
    val resourceAvailability = liveDataOf<Resource<Array<AvailabilityResponse.Availability>>>()

    fun getAvailability(product_name: String, locationId: Int) {
        resourceAvailability.postValue(Resource.loading())
        availabilityUseCase.execute<Array<AvailabilityResponse.Availability>> (
            ProductAvailabilityParam(ProductAvailabilityParam.Type.GET_AVAILABILITY, product_name, locationId)
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
}
