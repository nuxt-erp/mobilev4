package com.github.htdangkhoa.nexterp.data.repository.auth

import com.github.htdangkhoa.nexterp.base.BaseRepository
import com.github.htdangkhoa.nexterp.data.remote.auth.AuthResponse
import com.github.htdangkhoa.nexterp.data.remote.auth.login.LoginRequest
import com.github.htdangkhoa.nexterp.data.remote.auth.renew_token.RenewTokenRequest

interface AuthRepository : BaseRepository {
    suspend fun login(
        loginRequest: LoginRequest
    ): Result<AuthResponse.Token?>

    suspend fun renewToken(
        renewTokenRequest: RenewTokenRequest
    ): Result<AuthResponse.Token?>

    suspend fun logout(): Result<Array<String?>>
}
