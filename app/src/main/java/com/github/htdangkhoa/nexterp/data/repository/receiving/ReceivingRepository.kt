package com.github.htdangkhoa.nexterp.data.repository.receiving

import com.github.htdangkhoa.nexterp.base.BaseRepository
import com.github.htdangkhoa.nexterp.data.remote.auth.AuthResponse
import com.github.htdangkhoa.nexterp.data.remote.auth.login.LoginRequest
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving.ReceivingResponse
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving_details.ReceivingDetailsResponse

interface ReceivingRepository : BaseRepository {
    suspend fun getReceiving(): Result<Array<ReceivingResponse.Receiving>>
    suspend fun getReceivingDetails(id: Int): Result<Array<ReceivingDetailsResponse.ReceivingDetails>>
}

