package com.github.htdangkhoa.nexterp.ui.settings

import com.github.htdangkhoa.nexterp.data.repository.auth.AuthRepository
import com.github.htdangkhoa.nexterp.data.repository.auth.AuthRepositoryImp
import com.github.htdangkhoa.nexterp.data.repository.location.LocationRepository
import com.github.htdangkhoa.nexterp.data.repository.location.LocationRepositoryImp
import com.github.htdangkhoa.nexterp.data.repository.user.UserRepository
import com.github.htdangkhoa.nexterp.data.repository.user.UserRepositoryImp
import com.github.htdangkhoa.nexterp.data.service.ApiService
import com.github.htdangkhoa.nexterp.domain.auth.AuthUseCase
import com.github.htdangkhoa.nexterp.domain.location.LocationUseCase
import com.github.htdangkhoa.nexterp.domain.user.UserUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object SettingsModule {
    val module = module {
        single(override = true) { provideUserRepository(get()) }

        single(override = true) { provideUserUseCase(get()) }

        single(override = true) { provideAuthRepository(get()) }

        single(override = true) { provideAuthUseCase(get()) }

        single(override = true) { provideLocationRepository(get()) }

        single(override = true) { provideLocationUseCase(get()) }

        viewModel { SettingsViewModel(get(), get(), get()) }
    }

    private fun provideUserRepository(apiService: ApiService): UserRepository =
        UserRepositoryImp(apiService)

    private fun provideUserUseCase(userRepository: UserRepository) = UserUseCase(userRepository)

    private fun provideAuthRepository(apiService: ApiService): AuthRepository =
        AuthRepositoryImp(apiService)

    private fun provideAuthUseCase(authRepository: AuthRepository) = AuthUseCase(authRepository)

    private fun provideLocationRepository(apiService: ApiService): LocationRepository =
        LocationRepositoryImp(apiService)

    private fun provideLocationUseCase(locationRepository: LocationRepository) = LocationUseCase(locationRepository)

}
