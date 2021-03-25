package com.github.htdangkhoa.nexterp.data.repository.config
import com.github.htdangkhoa.nexterp.base.BaseRepositoryImp
import com.github.htdangkhoa.nexterp.data.remote.config.ConfigResponse
import com.github.htdangkhoa.nexterp.data.service.ApiService
import com.github.htdangkhoa.nexterp.extension.map
import retrofit2.HttpException

class ConfigRepositoryImp(
    apiService: ApiService
) : BaseRepositoryImp(apiService), ConfigRepository {

    override suspend fun getConfig(): Result<Array<ConfigResponse.Config>> {
        return try {
            val res = apiService.getConfig()
            Result.map(res)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }
}
