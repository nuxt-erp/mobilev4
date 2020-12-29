package com.github.htdangkhoa.nexterp.data.repository.category

import com.github.htdangkhoa.nexterp.base.BaseRepositoryImp
import com.github.htdangkhoa.nexterp.data.remote.category.CategoryResponse
import com.github.htdangkhoa.nexterp.data.service.ApiService
import com.github.htdangkhoa.nexterp.extension.map
import retrofit2.HttpException

class CategoryRepositoryImp(
    apiService: ApiService
) : BaseRepositoryImp(apiService), CategoryRepository {

    override suspend fun getCategory(
        list: Int,
        is_enabled: Int
    ): Result<Array<CategoryResponse.Category>> {
        return try {
            val res = apiService.getCategory(list, is_enabled)
            Result.map(res)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }
}
