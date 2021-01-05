package com.github.htdangkhoa.nexterp.data.repository.receiving

import com.github.htdangkhoa.nexterp.base.BaseRepository
import com.github.htdangkhoa.nexterp.data.remote.SuccessResponse
import com.github.htdangkhoa.nexterp.data.remote.auth.AuthResponse
import com.github.htdangkhoa.nexterp.data.remote.auth.login.LoginRequest
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving.NewReceivingRequest
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving.ReceivingObjectResponse
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving.ReceivingResponse
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving_details.ReceivingDetailsResponse
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving_details.UpdateReceivingRequest
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stockcount.NewStockCountRequest
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stockcount.StockCountResponse

interface ReceivingRepository : BaseRepository {
    suspend fun updateReceiving(
        id: Int,
        receivingRequest: UpdateReceivingRequest
    ): Result<ReceivingResponse.Receiving?>
    suspend fun newReceiving(
        receivingRequest: NewReceivingRequest
    ): Result<ReceivingResponse.Receiving?>
    suspend fun getReceiving(): Result<Array<ReceivingResponse.Receiving?>>
    suspend fun getReceivingDetails(id: Int): Result<Array<ReceivingDetailsResponse.ReceivingDetails?>>
    suspend fun finishReceiving(id: Int): Result<ReceivingResponse.Receiving?>
    suspend fun deleteReceiving(id: Int): Result<Array<ReceivingResponse.Receiving?>>
    suspend fun deleteReceivingDetail(id: Int): Result<Array<ReceivingDetailsResponse.ReceivingDetails?>>
}

