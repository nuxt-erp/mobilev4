package com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount

import com.github.htdangkhoa.nexterp.data.repository.auth.AuthRepository
import com.github.htdangkhoa.nexterp.data.repository.auth.AuthRepositoryImp
import com.github.htdangkhoa.nexterp.data.repository.stockcount.StockCountRepository
import com.github.htdangkhoa.nexterp.data.repository.stockcount.StockCountRepositoryImp
import com.github.htdangkhoa.nexterp.data.service.ApiService
import com.github.htdangkhoa.nexterp.domain.auth.AuthUseCase
import com.github.htdangkhoa.nexterp.domain.stockcount.StockCountUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object StockCountModule {
    val module = module {
        single(override = true) { provideStockCountRepository(get()) }

        single(override = true) { provideStockCountUseCase(get()) }

        single(override = true) { provideAuthRepository(get()) }

        single(override = true) { provideAuthUseCase(get()) }


        viewModel { StockCountViewModel(get(), get()) }
    }

    private fun provideStockCountRepository(apiService: ApiService): StockCountRepository =
        StockCountRepositoryImp(apiService)

    private fun provideStockCountUseCase(stockCountRepository: StockCountRepository) = StockCountUseCase(stockCountRepository)

    private fun provideAuthRepository(apiService: ApiService): AuthRepository =
        AuthRepositoryImp(apiService)

    private fun provideAuthUseCase(authRepository: AuthRepository) = AuthUseCase(authRepository)

}
