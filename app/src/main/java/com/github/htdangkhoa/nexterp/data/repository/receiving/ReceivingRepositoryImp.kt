package com.github.htdangkhoa.nexterp.data.repository.receiving

import com.github.htdangkhoa.nexterp.base.BaseRepositoryImp
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving.NewReceivingRequest
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving.ReceivingResponse
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving_details.ReceivingDetailsResponse
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving_details.UpdateReceivingRequest
import com.github.htdangkhoa.nexterp.data.service.ApiService
import com.github.htdangkhoa.nexterp.extension.map
import retrofit2.HttpException

class ReceivingRepositoryImp(
    apiService: ApiService
) : BaseRepositoryImp(apiService), ReceivingRepository {

    override suspend fun updateReceiving(
        id: Int,
        receivingRequest: UpdateReceivingRequest
    ): Result<ReceivingResponse.Receiving?> {
        return try {
            val res = apiService.updateReceiving(id, receivingRequest)
            Result.map(res)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    override suspend fun newReceiving(
        receivingRequest: NewReceivingRequest
    ): Result<ReceivingResponse.Receiving?> {
        return try {
            val res = apiService.newReceiving(receivingRequest)
            Result.map(res)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    override suspend fun getReceiving(receivingType: String): Result<Array<ReceivingResponse.Receiving?>> {
        return try {
            val res = apiService.getReceiving(receivingType)
            Result.map(res)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }

    override suspend fun getReceivingDetails(id: Int): Result<Array<ReceivingDetailsResponse.ReceivingDetails?>> {
        return try {
            val res = apiService.getReceivingDetails(id)
            Result.map(res)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }

    override suspend fun finishReceiving(id: Int): Result<ReceivingResponse.Receiving?> {
        return try {
            val res = apiService.finishReceiving(id)
            Result.map(res)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }

    override suspend fun voidReceiving(id: Int): Result<Int> {
        return try {
            val res = apiService.voidReceiving(id)
            Result.success(res.code)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }

    override suspend fun deleteReceivingDetail(id: Int): Result<Array<ReceivingDetailsResponse.ReceivingDetails?>> {
        return try {
            val res = apiService.deleteReceivingDetail(id)
            Result.map(res)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }
}
