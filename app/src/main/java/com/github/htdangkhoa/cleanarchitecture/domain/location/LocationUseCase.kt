package com.github.htdangkhoa.cleanarchitecture.domain.location
import com.github.htdangkhoa.cleanarchitecture.base.BaseUseCase
import com.github.htdangkhoa.cleanarchitecture.data.repository.location.LocationRepository

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
