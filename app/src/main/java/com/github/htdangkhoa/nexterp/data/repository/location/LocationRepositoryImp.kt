package com.github.htdangkhoa.nexterp.data.repository.location

import android.util.Log
import com.github.htdangkhoa.nexterp.base.BaseRepositoryImp
import com.github.htdangkhoa.nexterp.data.remote.location.LocationResponse
import com.github.htdangkhoa.nexterp.data.service.ApiService
import com.github.htdangkhoa.nexterp.extension.map
import retrofit2.HttpException

class LocationRepositoryImp(
    apiService: ApiService
) : BaseRepositoryImp(apiService), LocationRepository {

    override suspend fun getLocations(): Result<Array<LocationResponse.Location>> {
        return try {
            val res = apiService.getLocations()
            Result.map(res)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }
}
