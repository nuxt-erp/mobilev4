package com.github.htdangkhoa.nexterp.data.repository.product

import com.github.htdangkhoa.nexterp.base.BaseRepositoryImp
import com.github.htdangkhoa.nexterp.data.remote.product.ProductResponse
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving.ReceivingResponse
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving_details.ReceivingDetailsResponse
import com.github.htdangkhoa.nexterp.data.service.ApiService
import com.github.htdangkhoa.nexterp.extension.map
import retrofit2.HttpException

class ProductRepositoryImp(
    apiService: ApiService
) : BaseRepositoryImp(apiService), ProductRepository {

    override suspend fun getProduct(searchable: String, location_id: Int?): Result<Array<ProductResponse.Product>> {
        return try {
            val res = apiService.getProduct(searchable, location_id)
            Result.map(res)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }
}
