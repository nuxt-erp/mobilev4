package com.github.htdangkhoa.nexterp.data.repository.receiving

import android.util.Log
import com.github.htdangkhoa.nexterp.base.BaseRepositoryImp
import com.github.htdangkhoa.nexterp.data.remote.receiving.ReceivingResponse
import com.github.htdangkhoa.nexterp.data.service.ApiService
import com.github.htdangkhoa.nexterp.extension.map
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
