package com.github.htdangkhoa.cleanarchitecture.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import com.github.htdangkhoa.cleanarchitecture.Constant
import com.github.htdangkhoa.cleanarchitecture.data.remote.auth.AuthResponse
import com.github.htdangkhoa.cleanarchitecture.data.remote.auth.login.LoginRequest
import com.github.htdangkhoa.cleanarchitecture.domain.auth.AuthParam
import com.github.htdangkhoa.cleanarchitecture.domain.auth.AuthUseCase
import com.github.htdangkhoa.cleanarchitecture.extension.liveDataOf
import com.github.htdangkhoa.cleanarchitecture.resource.Resource

class LoginViewModel(
    private val authUseCase: AuthUseCase
) : ViewModel() {
    val resourceToken = liveDataOf<Resource<AuthResponse.Token>>()

    fun login(username: String, password: String) {
        val request = LoginRequest(
            username.trim(),
            password,
            clientSecret = Constant.CLIENT_SECRET,
            clientId = Constant.CLIENT_ID,
            grantType = Constant.GRANT_TYPE
            )

        resourceToken.postValue(Resource.loading())

        authUseCase.execute<AuthResponse.Token>(AuthParam(request)) {
            onComplete {
                Log.e("it->>>", "$it")
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
