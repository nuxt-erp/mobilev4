package com.github.htdangkhoa.nexterp.data.repository.config

import com.github.htdangkhoa.nexterp.base.BaseRepository
import com.github.htdangkhoa.nexterp.data.remote.config.ConfigResponse

interface ConfigRepository : BaseRepository {
    suspend fun getConfig(): Result<Array<ConfigResponse.Config>>
}

