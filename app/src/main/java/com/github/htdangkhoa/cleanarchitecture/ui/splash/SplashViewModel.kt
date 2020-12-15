package com.github.htdangkhoa.cleanarchitecture.ui.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import com.github.htdangkhoa.cleanarchitecture.Constant
import com.github.htdangkhoa.cleanarchitecture.data.remote.auth.AuthResponse
import com.github.htdangkhoa.cleanarchitecture.data.remote.auth.renew_token.RenewTokenRequest
import com.github.htdangkhoa.cleanarchitecture.domain.auth.AuthParam
import com.github.htdangkhoa.cleanarchitecture.domain.auth.AuthUseCase
import com.github.htdangkhoa.cleanarchitecture.extension.liveDataOf
import com.github.htdangkhoa.cleanarchitecture.resource.Resource

class SplashViewModel(private val authUseCase: AuthUseCase) : ViewModel() {
    val resourceToken = liveDataOf<Resource<AuthResponse.Token>>()

    fun renewToken(refreshToken: String) {
        resourceToken.postValue(Resource.loading())

        val request = RenewTokenRequest(refreshToken,
            client_secret = Constant.CLIENT_SECRET,
            client_id = Constant.CLIENT_ID,
            grant_type = "refresh_token"
        )

        authUseCase.execute<AuthResponse.Token>(AuthParam(request)) {
            onComplete {
                resourceToken.postValue(Resource.success(it))
            }

            onError {
                resourceToken.postValue(Resource.error(it))
            }

            onCancel {
                resourceToken.postValue(Resource.error(it))
            }
        }
    }
}
