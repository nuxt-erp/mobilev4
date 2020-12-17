package com.github.htdangkhoa.nexterp.ui.splash

import com.github.htdangkhoa.nexterp.data.repository.auth.AuthRepository
import com.github.htdangkhoa.nexterp.data.repository.auth.AuthRepositoryImp
import com.github.htdangkhoa.nexterp.data.service.ApiService
import com.github.htdangkhoa.nexterp.domain.auth.AuthUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object SplashModule {
    val module = module {
        single(override = true) { provideAuthRepository(get()) }

        single(override = true) { provideAuthUseCase(get()) }

        viewModel { SplashViewModel(get()) }
    }

    private fun provideAuthRepository(apiService: ApiService): AuthRepository =
        AuthRepositoryImp(apiService)

    private fun provideAuthUseCase(authRepository: AuthRepository) = AuthUseCase(authRepository)
}
