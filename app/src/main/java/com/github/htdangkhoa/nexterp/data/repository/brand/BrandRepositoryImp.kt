package com.github.htdangkhoa.nexterp.data.repository.brand

import com.github.htdangkhoa.nexterp.base.BaseRepositoryImp
import com.github.htdangkhoa.nexterp.data.remote.brand.BrandResponse
import com.github.htdangkhoa.nexterp.data.service.ApiService
import com.github.htdangkhoa.nexterp.extension.map
import retrofit2.HttpException

class BrandRepositoryImp(
    apiService: ApiService
) : BaseRepositoryImp(apiService), BrandRepository {

    override suspend fun getBrand(
        list: Int,
        is_enabled: Int,
        per_page: Int?
    ): Result<Array<BrandResponse.Brand>> {
        return try {
            val res = apiService.getBrand(list, is_enabled, per_page)
            Result.map(res)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }
}
