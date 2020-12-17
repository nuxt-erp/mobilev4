package com.github.htdangkhoa.cleanarchitecture.ui.receiving

import com.github.htdangkhoa.cleanarchitecture.data.repository.auth.AuthRepository
import com.github.htdangkhoa.cleanarchitecture.data.repository.auth.AuthRepositoryImp
import com.github.htdangkhoa.cleanarchitecture.data.repository.location.LocationRepository
import com.github.htdangkhoa.cleanarchitecture.data.repository.location.LocationRepositoryImp
import com.github.htdangkhoa.cleanarchitecture.data.repository.receiving.ReceivingRepository
import com.github.htdangkhoa.cleanarchitecture.data.repository.receiving.ReceivingRepositoryImp
import com.github.htdangkhoa.cleanarchitecture.data.repository.user.UserRepository
import com.github.htdangkhoa.cleanarchitecture.data.repository.user.UserRepositoryImp
import com.github.htdangkhoa.cleanarchitecture.data.service.ApiService
import com.github.htdangkhoa.cleanarchitecture.domain.auth.AuthUseCase
import com.github.htdangkhoa.cleanarchitecture.domain.location.LocationUseCase
import com.github.htdangkhoa.cleanarchitecture.domain.receiving.ReceivingUseCase
import com.github.htdangkhoa.cleanarchitecture.domain.user.UserUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ReceivingModule {
    val module = module {
        single(override = true) { provideReceivingRepository(get()) }

        single(override = true) { provideReceivingUseCase(get()) }

        single(override = true) { provideAuthRepository(get()) }

        single(override = true) { provideAuthUseCase(get()) }


        viewModel { ReceivingViewModel(get(), get()) }
    }

    private fun provideReceivingRepository(apiService: ApiService): ReceivingRepository =
        ReceivingRepositoryImp(apiService)

    private fun provideReceivingUseCase(receivingRepository: ReceivingRepository) = ReceivingUseCase(receivingRepository)

    private fun provideAuthRepository(apiService: ApiService): AuthRepository =
        AuthRepositoryImp(apiService)

    private fun provideAuthUseCase(authRepository: AuthRepository) = AuthUseCase(authRepository)

}
