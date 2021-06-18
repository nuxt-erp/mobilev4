package com.github.htdangkhoa.nexterp.data.repository.locationbin

import com.github.htdangkhoa.nexterp.base.BaseRepository
import com.github.htdangkhoa.nexterp.data.remote.locationbin.BinResponse

interface BinRepository : BaseRepository {
    suspend fun getBin(barcode: String?, location_id: Int?, list: Int, is_enabled: Int, per_page: Int?): Result<Array<BinResponse.Bin>>
}
