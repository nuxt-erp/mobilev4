package com.github.htdangkhoa.nexterp.data.repository.locationbin

import com.github.htdangkhoa.nexterp.base.BaseRepositoryImp
import com.github.htdangkhoa.nexterp.data.remote.locationbin.BinResponse
import com.github.htdangkhoa.nexterp.data.service.ApiService
import com.github.htdangkhoa.nexterp.extension.map
import retrofit2.HttpException

class BinRepositoryImp(
    apiService: ApiService
) : BaseRepositoryImp(apiService), BinRepository {

    override suspend fun getBin(
        barcode: String?,
        location_id: Int?,
        list: Int,
        is_enabled: Int
    ): Result<Array<BinResponse.Bin>> {
        return try {
            val res = apiService.getBin(barcode, location_id, list, is_enabled)
            Result.map(res)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }
}
