package com.github.htdangkhoa.nexterp.data.service

import com.github.htdangkhoa.nexterp.data.remote.SuccessResponse
import com.github.htdangkhoa.nexterp.data.remote.auth.AuthResponse
import com.github.htdangkhoa.nexterp.data.remote.auth.login.LoginRequest
import com.github.htdangkhoa.nexterp.data.remote.auth.renew_token.RenewTokenRequest
import com.github.htdangkhoa.nexterp.data.remote.location.LocationResponse
import com.github.htdangkhoa.nexterp.data.remote.receiving.ReceivingResponse
import com.github.htdangkhoa.nexterp.data.remote.stockcount.StockCountResponse
import com.github.htdangkhoa.nexterp.data.remote.user.GetMeResponse
import com.github.htdangkhoa.nexterp.data.remote.user.UsersResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    // Auth

    @POST("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): AuthResponse

    @POST("renew_token")
    suspend fun renewToken(
        @Body renewTokenRequest: RenewTokenRequest
    ): AuthResponse

    // User

    @GET("me")
    suspend fun getMe(): GetMeResponse

    @GET("general/users?list=1")
    suspend fun getUsers(): UsersResponse

    @GET("logout")
    suspend fun logout(): SuccessResponse

    // Location

    @GET("general/locations?is_enabled=1&list=1")
    suspend fun getLocations(): LocationResponse

    // Receiving

    @GET("inventory/receiving")
    suspend fun getReceiving(): ReceivingResponse

    // Stock Count

    @GET("inventory/stock_count")
    suspend fun getStockCount(): StockCountResponse
}
