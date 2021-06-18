package com.github.htdangkhoa.nexterp.data.repository.tag

import com.github.htdangkhoa.nexterp.base.BaseRepositoryImp
import com.github.htdangkhoa.nexterp.data.remote.tag.TagResponse
import com.github.htdangkhoa.nexterp.data.service.ApiService
import com.github.htdangkhoa.nexterp.extension.map
import retrofit2.HttpException

class TagRepositoryImp(
    apiService: ApiService
) : BaseRepositoryImp(apiService), TagRepository {

    override suspend fun getTag(
        list: Int,
        per_page: Int?
    ): Result<Array<TagResponse.Tag>> {
        return try {
            val res = apiService.getTag(list, per_page)
            Result.map(res)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }
}
