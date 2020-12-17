package com.github.htdangkhoa.nexterp.domain.location
import com.github.htdangkhoa.nexterp.base.BaseUseCase
import com.github.htdangkhoa.nexterp.data.repository.location.LocationRepository

class LocationUseCase(
    repository: LocationRepository
) : BaseUseCase<LocationRepository, LocationParam>(repository) {
    override suspend fun buildUseCase(params: LocationParam?): Result<*> {
        return when (params?.type) {
            LocationParam.Type.GET_LOCATIONS -> repository.getLocations()
            else -> throw UnsupportedOperationException("This request is not support in this case!")
        }
    }
}
