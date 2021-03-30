package com.github.htdangkhoa.nexterp.ui.main.fragments.stockadjustment

import com.github.htdangkhoa.nexterp.data.repository.availability.ProductAvailabilityRepository
import com.github.htdangkhoa.nexterp.data.repository.availability.ProductAvailabilityRepositoryImp
import com.github.htdangkhoa.nexterp.data.repository.stockadjustment.StockAdjustmentRepository
import com.github.htdangkhoa.nexterp.data.repository.stockadjustment.StockAdjustmentRepositoryImp
import com.github.htdangkhoa.nexterp.data.service.ApiService
import com.github.htdangkhoa.nexterp.domain.availability.ProductAvailabilityUseCase
import com.github.htdangkhoa.nexterp.domain.stockadjustment.StockAdjustmentUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object StockAdjustmentModule {
    val module = module {
        single(override = true) { provideStockAdjustmentRepository(get()) }

        single(override = true) { provideStockAdjustmentUseCase(get()) }

        single(override = true) { provideAvailabilityRepository(get()) }

        single(override = true) { provideAvailabilityUseCase(get()) }

        viewModel { StockAdjustmentViewModel(get(), get()) }
    }
    private fun provideAvailabilityRepository(apiService: ApiService): ProductAvailabilityRepository =
        ProductAvailabilityRepositoryImp(apiService)

    private fun provideAvailabilityUseCase(availabilityRepository: ProductAvailabilityRepository) = ProductAvailabilityUseCase(availabilityRepository)

    private fun provideStockAdjustmentRepository(apiService: ApiService): StockAdjustmentRepository =
        StockAdjustmentRepositoryImp(apiService)

    private fun provideStockAdjustmentUseCase(stockAdjustmentRepository: StockAdjustmentRepository) = StockAdjustmentUseCase(stockAdjustmentRepository)
}
