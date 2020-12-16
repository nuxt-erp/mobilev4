package com.github.htdangkhoa.cleanarchitecture.data.repository.location

import com.github.htdangkhoa.cleanarchitecture.base.BaseRepository
import com.github.htdangkhoa.cleanarchitecture.data.remote.location.LocationResponse

interface LocationRepository : BaseRepository {
    suspend fun getLocations(): Result<Array<LocationResponse.Location>>
}
