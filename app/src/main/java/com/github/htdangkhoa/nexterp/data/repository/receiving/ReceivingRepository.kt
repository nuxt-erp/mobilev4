package com.github.htdangkhoa.nexterp.data.repository.receiving

import com.github.htdangkhoa.nexterp.base.BaseRepository
import com.github.htdangkhoa.nexterp.data.remote.receiving.ReceivingResponse

interface ReceivingRepository : BaseRepository {
    suspend fun getReceiving(): Result<Array<ReceivingResponse.Receiving>>
}
