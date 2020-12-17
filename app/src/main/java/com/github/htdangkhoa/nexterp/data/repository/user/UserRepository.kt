package com.github.htdangkhoa.nexterp.data.repository.user

import com.github.htdangkhoa.nexterp.base.BaseRepository
import com.github.htdangkhoa.nexterp.data.remote.user.GetMeResponse
import com.github.htdangkhoa.nexterp.data.remote.user.UsersResponse

interface UserRepository : BaseRepository {
    suspend fun getMe(): Result<GetMeResponse.User?>
    suspend fun getUsers(): Result<Array<UsersResponse.User>>
}
