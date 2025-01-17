package com.github.htdangkhoa.nexterp.ui.main.fragments.receiving_purchase

import com.github.htdangkhoa.nexterp.data.repository.product.ProductRepository
import com.github.htdangkhoa.nexterp.data.repository.product.ProductRepositoryImp
import com.github.htdangkhoa.nexterp.data.repository.receiving.ReceivingRepository
import com.github.htdangkhoa.nexterp.data.repository.receiving.ReceivingRepositoryImp
import com.github.htdangkhoa.nexterp.data.service.ApiService
import com.github.htdangkhoa.nexterp.domain.product.ProductUseCase
import com.github.htdangkhoa.nexterp.domain.receiving.ReceivingUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ReceivingModule {
    val module = module {
        single(override = true) { provideReceivingRepository(get()) }

        single(override = true) { provideReceivingUseCase(get()) }

        single(override = true) { provideProductRepository(get()) }

        single(override = true) { provideProductUseCase(get()) }


        viewModel { ReceivingViewModel(get(), get()) }
    }

    private fun provideReceivingRepository(apiService: ApiService): ReceivingRepository =
        ReceivingRepositoryImp(apiService)

    private fun provideReceivingUseCase(receivingRepository: ReceivingRepository) =
        ReceivingUseCase(receivingRepository)

    private fun provideProductRepository(apiService: ApiService): ProductRepository =
        ProductRepositoryImp(apiService)

    private fun provideProductUseCase(productRepository: ProductRepository) =
        ProductUseCase(productRepository)

}
