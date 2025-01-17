package com.github.htdangkhoa.nexterp.data.service
import com.github.htdangkhoa.nexterp.data.remote.SuccessResponse
import com.github.htdangkhoa.nexterp.data.remote.auth.AuthResponse
import com.github.htdangkhoa.nexterp.data.remote.auth.login.LoginRequest
import com.github.htdangkhoa.nexterp.data.remote.auth.renew_token.RenewTokenRequest
import com.github.htdangkhoa.nexterp.data.remote.availability.AvailabilityResponse
import com.github.htdangkhoa.nexterp.data.remote.availability.NewAvailabilityRequest
import com.github.htdangkhoa.nexterp.data.remote.availability.ProductAvailabilityObjectResponse
import com.github.htdangkhoa.nexterp.data.remote.availability.ProductAvailabilityResponse
import com.github.htdangkhoa.nexterp.data.remote.locationbin.BinResponse
import com.github.htdangkhoa.nexterp.data.remote.brand.BrandResponse
import com.github.htdangkhoa.nexterp.data.remote.category.CategoryResponse
import com.github.htdangkhoa.nexterp.data.remote.config.ConfigResponse
import com.github.htdangkhoa.nexterp.data.remote.location.LocationResponse
import com.github.htdangkhoa.nexterp.data.remote.product.ProductResponse
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving.NewReceivingRequest
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving.ReceivingObjectResponse
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving.ReceivingResponse
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving_details.ReceivingDetailsResponse
import com.github.htdangkhoa.nexterp.data.remote.receiving.receiving_details.UpdateReceivingRequest
import com.github.htdangkhoa.nexterp.data.remote.stockadjustment.stock_adjustment_details.StockAdjustmentDetailResponse
import com.github.htdangkhoa.nexterp.data.remote.stockadjustment.stock_adjustment_details.UpdateStockAdjustmentRequest
import com.github.htdangkhoa.nexterp.data.remote.stockadjustment.stockadjustment.NewStockAdjustmentRequest
import com.github.htdangkhoa.nexterp.data.remote.stockadjustment.stockadjustment.StockAdjustmentObjectResponse
import com.github.htdangkhoa.nexterp.data.remote.stockadjustment.stockadjustment.StockAdjustmentResponse
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stock_count_details.StockCountDetailResponse
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stock_count_details.UpdateStockCountRequest
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stockcount.NewStockCountRequest
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stockcount.StockCountObjectResponse
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stockcount.StockCountResponse
import com.github.htdangkhoa.nexterp.data.remote.stocklocator.StockLocatorResponse
import com.github.htdangkhoa.nexterp.data.remote.tag.TagResponse
import com.github.htdangkhoa.nexterp.data.remote.user.GetMeResponse
import com.github.htdangkhoa.nexterp.data.remote.user.UsersResponse
import retrofit2.http.*

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

    @GET("general/configs")
    suspend fun getConfig(): ConfigResponse

    // Receiving

    @GET("inventory/receiving")
    suspend fun getReceiving(@Query("receiving_type") receiving_type: String): ReceivingResponse

    @GET("inventory/receiving_details")
    suspend fun getReceivingDetails(@Query("receiving_id") receiving_id: Int) : ReceivingDetailsResponse

    @PUT("inventory/receiving/{id}")
    suspend fun updateReceiving(@Path("id") id: Int, @Body updateReceivingRequest: UpdateReceivingRequest) : ReceivingObjectResponse

    @POST("inventory/receiving")
    suspend fun newReceiving(@Body request: NewReceivingRequest): ReceivingObjectResponse

    @DELETE("inventory/receiving/{id}")
    suspend fun voidReceiving(@Path("id") id: Int) : ReceivingObjectResponse

    @DELETE("inventory/receiving_details/{id}")
    suspend fun deleteReceivingDetail(@Path("id") id: Int) : ReceivingDetailsResponse

    @GET("inventory/receiving/finish/{id}")
    suspend fun finishReceiving(@Path("id") id: Int) : ReceivingObjectResponse

    // Stock Count

    @GET("inventory/stock_count" + "?not_received=1")
    suspend fun getStockCount(): StockCountResponse

    @GET("inventory/stock_count_details")
    suspend fun getStockCountDetails(@Query("stockcount_id") stockcount_id: Int) : StockCountDetailResponse

    @PUT("inventory/stock_count/{id}")
    suspend fun updateStockCount(@Path("id") id: Int, @Body updateStockCountRequest: UpdateStockCountRequest) : StockCountObjectResponse

    @POST("inventory/stock_count")
    suspend fun newStockCount(@Body request: NewStockCountRequest): StockCountObjectResponse

    @GET("inventory/stock_count/void/{id}")
    suspend fun voidStockCount(@Path("id") id: Int) : StockCountObjectResponse

    @DELETE("inventory/stock_count_details/{id}")
    suspend fun deleteStockCountDetail(@Path("id") id: Int) : StockCountDetailResponse

    @GET("inventory/stock_count/finish/{id}")
    suspend fun finishStockCount(@Path("id") id: Int) : StockCountResponse

    // Stock Adjustment

    @GET("inventory/stock_adjustments" + "?not_adjusted=1")
    suspend fun getStockAdjustment(): StockAdjustmentResponse

    @GET("inventory/stock_adjustment_details")
    suspend fun getStockAdjustmentDetails(@Query("stock_adjustment_id") stock_adjustment_id: Int) : StockAdjustmentDetailResponse

    @PUT("inventory/stock_adjustments/{id}")
    suspend fun updateStockAdjustment(@Path("id") id: Int, @Body updateStockAdjustmentRequest: UpdateStockAdjustmentRequest) : StockAdjustmentObjectResponse

    @POST("inventory/stock_adjustments")
    suspend fun newStockAdjustment(@Body request: NewStockAdjustmentRequest): StockAdjustmentObjectResponse

    @GET("inventory/stock_adjustments/void/{id}")
    suspend fun voidStockAdjustment(@Path("id") id: Int) : StockAdjustmentObjectResponse

    @DELETE("inventory/stock_adjustment_details/{id}")
    suspend fun deleteStockAdjustmentDetail(@Path("id") id: Int) : StockAdjustmentDetailResponse


    // Bin

    @GET("inventory/location_bins")
    suspend fun getBin(
        @Query("barcode") barcode: String? = null,
        @Query("location_id") location_id: Int? = null,
        @Query("list") list: Int = 1,
        @Query("is_enabled") is_enabled: Int = 1,
        @Query("per_page") per_page: Int? = 20
    ):BinResponse

    // Category

    @GET("inventory/categories")
    suspend fun getCategory(
        @Query("list") list: Int = 1,
        @Query("is_enabled") is_enabled: Int = 1,
        @Query("per_page") per_page: Int? = 20

    ):CategoryResponse

    // Tag

    @GET("general/tags")
    suspend fun getTag(
        @Query("list") list: Int = 1,
        @Query("per_page") per_page: Int? = 20

    ):TagResponse

    // Brand

    @GET("inventory/brands")
    suspend fun getBrand(
        @Query("list") list: Int = 1,
        @Query("is_enabled") is_enabled: Int = 1,
        @Query("per_page") per_page: Int? = 20

    ):BrandResponse

    // Stock Locator

    @GET("inventory/stock_locator")
    suspend fun getStockLocator(
        @Query("list") list: Int = 1,
        @Query("is_enabled") is_enabled: Int = 1,
        @Query("per_page") per_page: Int? = 20

    ):StockLocatorResponse

    // Availabilities

    @GET("inventory/start_stock_count_mobile")
    suspend fun getProductAvailabilities(
        @Query("searchable") searchable: String? = null,
        @Query("bin_searchable") bin_searchable: String? = null,
        @Query("location_id") location_id: Int? = null,
        @Query("brand_ids[]") brand_ids: List<Long>? = null,
        @Query("tag_ids[]") tag_ids: List<Long>? = null,
        @Query("bin_ids[]") bin_ids: List<Long>? = null,
        @Query("category_ids[]") category_ids: List<Long>? = null,
        @Query("stock_locator_ids[]") stock_locator_ids: List<Long>? = null
    ): ProductAvailabilityResponse

    @GET("inventory/availabilities_by_barcode")
    suspend fun getAvailabilities(
        @Query("product_name") product_name: String? = null,
        @Query("bin_barcode") bin_barcode: String? = null,
        @Query("location_id") location_id: Int? = null,
    ): AvailabilityResponse

    @POST("inventory/new_availability")
    suspend fun newAvailability(@Body request: NewAvailabilityRequest): ProductAvailabilityObjectResponse

    // Product

    @GET("inventory/products")
    suspend fun getProduct(
        @Query("searchable") searchable: String,
        @Query("location_id") location_id: Int? = null,
    ):ProductResponse



}
