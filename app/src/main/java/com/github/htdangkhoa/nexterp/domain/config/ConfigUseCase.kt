package com.github.htdangkhoa.nexterp.domain.config
import com.github.htdangkhoa.nexterp.base.BaseUseCase
import com.github.htdangkhoa.nexterp.data.repository.config.ConfigRepository

class ConfigUseCase(
    repository: ConfigRepository
) : BaseUseCase<ConfigRepository, ConfigParam>(repository) {
    override suspend fun buildUseCase(params: ConfigParam?): Result<*> {
        return when (params?.type) {
            ConfigParam.Type.GET_CONFIG -> repository.getConfig()
            else -> throw UnsupportedOperationException("This request is not support in this case!")
        }
    }
}
