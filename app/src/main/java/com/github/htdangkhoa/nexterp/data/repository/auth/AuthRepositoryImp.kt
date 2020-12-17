package com.github.htdangkhoa.nexterp.data.repository.auth

import com.github.htdangkhoa.nexterp.base.BaseRepositoryImp
import com.github.htdangkhoa.nexterp.data.remote.auth.AuthResponse
import com.github.htdangkhoa.nexterp.data.remote.auth.login.LoginRequest
import com.github.htdangkhoa.nexterp.data.remote.auth.renew_token.RenewTokenRequest
import com.github.htdangkhoa.nexterp.data.service.ApiService
import com.github.htdangkhoa.nexterp.extension.map

class AuthRepositoryImp(
    apiService: ApiService
) : BaseRepositoryImp(apiService), AuthRepository {
    override suspend fun login(loginRequest: LoginRequest): Result<AuthResponse.Token?> {
        return try {
            val res = apiService.login(loginRequest)

            Result.map(res)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun renewToken(
        renewTokenRequest: RenewTokenRequest
    ): Result<AuthResponse.Token?> {
        return try {
            val res = apiService.renewToken(renewTokenRequest)

            Result.map(res)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout(): Result<String?> {
        return try {
            val res = apiService.logout()

            Result.success(res.data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
