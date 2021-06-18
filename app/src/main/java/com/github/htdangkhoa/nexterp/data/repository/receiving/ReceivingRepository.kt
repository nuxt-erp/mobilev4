package com.github.htdangkhoa.nexterp.data.repository.receiving

import com.github.htdangkhoa.nexterp.base.BaseRepository
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving.NewReceivingRequest
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving.ReceivingResponse
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving_details.ReceivingDetailsResponse
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving_details.UpdateReceivingRequest

interface ReceivingRepository : BaseRepository {
    suspend fun updateReceiving(
        id: Int,
        receivingRequest: UpdateReceivingRequest
    ): Result<ReceivingResponse.Receiving?>
    suspend fun newReceiving(
        receivingRequest: NewReceivingRequest
    ): Result<ReceivingResponse.Receiving?>
    suspend fun getReceiving(receivingType: String): Result<Array<ReceivingResponse.Receiving?>>
    suspend fun getReceivingDetails(id: Int): Result<Array<ReceivingDetailsResponse.ReceivingDetails?>>
    suspend fun finishReceiving(id: Int): Result<ReceivingResponse.Receiving?>
    suspend fun voidReceiving(id: Int): Result<Int>
    suspend fun deleteReceivingDetail(id: Int): Result<Array<ReceivingDetailsResponse.ReceivingDetails?>>
}

