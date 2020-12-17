package com.github.htdangkhoa.nexterp.data.repository.location

import com.github.htdangkhoa.nexterp.base.BaseRepository
import com.github.htdangkhoa.nexterp.data.remote.location.LocationResponse

interface LocationRepository : BaseRepository {
    suspend fun getLocations(): Result<Array<LocationResponse.Location>>
}
