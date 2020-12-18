package com.github.htdangkhoa.nexterp.data.repository.product

import com.github.htdangkhoa.nexterp.base.BaseRepository
import com.github.htdangkhoa.nexterp.data.remote.product.ProductResponse

interface ProductRepository : BaseRepository {
    suspend fun getProduct(searchable: String, location_id: Int): Result<Array<ProductResponse.Product>>
}

