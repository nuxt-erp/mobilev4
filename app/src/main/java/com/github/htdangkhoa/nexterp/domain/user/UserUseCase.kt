package com.github.htdangkhoa.nexterp.domain.user
import com.github.htdangkhoa.nexterp.base.BaseUseCase
import com.github.htdangkhoa.nexterp.data.repository.user.UserRepository

class UserUseCase(
    repository: UserRepository
) : BaseUseCase<UserRepository, UserParam>(repository) {
    override suspend fun buildUseCase(params: UserParam?): Result<*> {
        return when (params?.type) {
            UserParam.Type.GET_ME -> repository.getMe()
            UserParam.Type.GET_USERS -> repository.getUsers()

            else -> throw UnsupportedOperationException("This request is not support in this case!")
        }
    }
}