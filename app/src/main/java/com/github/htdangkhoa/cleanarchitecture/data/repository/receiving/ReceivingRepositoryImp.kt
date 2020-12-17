package com.github.htdangkhoa.cleanarchitecture.data.repository.receiving

import android.util.Log
import com.github.htdangkhoa.cleanarchitecture.base.BaseRepositoryImp
import com.github.htdangkhoa.cleanarchitecture.data.remote.receiving.ReceivingResponse
import com.github.htdangkhoa.cleanarchitecture.data.service.ApiService
import com.github.htdangkhoa.cleanarchitecture.extension.map
import retrofit2.HttpException

class ReceivingRepositoryImp(
    apiService: ApiService
) : BaseRepositoryImp(apiService), ReceivingRepository {

    override suspend fun getReceiving(): Result<Array<ReceivingResponse.Receiving>> {
        return try {
            val res = apiService.getReceiving()

            Log.e("res->>>", res.toString())
            Result.map(res)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }
}
