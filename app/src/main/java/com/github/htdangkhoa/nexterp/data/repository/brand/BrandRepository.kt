package com.github.htdangkhoa.nexterp.data.repository.brand

import com.github.htdangkhoa.nexterp.base.BaseRepository
import com.github.htdangkhoa.nexterp.data.remote.brand.BrandResponse

interface BrandRepository : BaseRepository {
    suspend fun getBrand(list: Int, is_enabled: Int): Result<Array<BrandResponse.Brand>>
}
