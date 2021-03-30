package com.github.htdangkhoa.nexterp.ui.main.fragments.availability

import com.github.htdangkhoa.nexterp.data.repository.availability.ProductAvailabilityRepository
import com.github.htdangkhoa.nexterp.data.repository.availability.ProductAvailabilityRepositoryImp
import com.github.htdangkhoa.nexterp.data.service.ApiService
import com.github.htdangkhoa.nexterp.domain.availability.ProductAvailabilityUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AvailabilityModule {
    val module = module {
        single(override = true) { provideAvailabilityRepository(get()) }
        single(override = true) { provideAvailabilityUseCase(get()) }

        viewModel { AvailabilityViewModel(get()) }
    }

    private fun provideAvailabilityRepository(apiService: ApiService): ProductAvailabilityRepository =
        ProductAvailabilityRepositoryImp(apiService)

    private fun provideAvailabilityUseCase(availabilityRepository: ProductAvailabilityRepository) =
        ProductAvailabilityUseCase(availabilityRepository)



}
