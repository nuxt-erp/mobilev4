package com.github.htdangkhoa.cleanarchitecture.data.repository.location

import android.util.Log
import com.github.htdangkhoa.cleanarchitecture.base.BaseRepositoryImp
import com.github.htdangkhoa.cleanarchitecture.data.remote.location.LocationResponse
import com.github.htdangkhoa.cleanarchitecture.data.service.ApiService
import com.github.htdangkhoa.cleanarchitecture.extension.map
import retrofit2.HttpException

class LocationRepositoryImp(
    apiService: ApiService
) : BaseRepositoryImp(apiService), LocationRepository {

    override suspend fun getLocations(): Result<Array<LocationResponse.Location>> {
        return try {
            val res = apiService.getLocations()

            Log.e("res->>>", res.toString())
            Result.map(res)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }
}
