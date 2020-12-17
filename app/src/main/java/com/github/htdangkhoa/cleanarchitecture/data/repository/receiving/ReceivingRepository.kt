package com.github.htdangkhoa.cleanarchitecture.data.repository.receiving

import com.github.htdangkhoa.cleanarchitecture.base.BaseRepository
import com.github.htdangkhoa.cleanarchitecture.data.remote.location.LocationResponse
import com.github.htdangkhoa.cleanarchitecture.data.remote.receiving.ReceivingResponse

interface ReceivingRepository : BaseRepository {
    suspend fun getReceiving(): Result<Array<ReceivingResponse.Receiving>>
}
