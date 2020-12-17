package com.github.htdangkhoa.nexterp.data.repository.receiving

import com.github.htdangkhoa.nexterp.base.BaseRepositoryImp
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving.ReceivingResponse
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving_details.ReceivingDetailsResponse
import com.github.htdangkhoa.nexterp.data.service.ApiService
import com.github.htdangkhoa.nexterp.extension.map
import retrofit2.HttpException

class ReceivingRepositoryImp(
    apiService: ApiService
) : BaseRepositoryImp(apiService), ReceivingRepository {

    override suspend fun getReceiving(): Result<Array<ReceivingResponse.Receiving>> {
        return try {
            val res = apiService.getReceiving()
            Result.map(res)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }

    override suspend fun getReceivingDetails(id: Int): Result<Array<ReceivingDetailsResponse.ReceivingDetails>> {
        return try {
            val res = apiService.getReceivingDetails(id)
            Result.map(res)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }
}
