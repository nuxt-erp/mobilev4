package com.github.htdangkhoa.cleanarchitecture.ui.main

import androidx.lifecycle.ViewModel
import com.github.htdangkhoa.cleanarchitecture.data.remote.user.GetMeResponse
import com.github.htdangkhoa.cleanarchitecture.data.remote.user.UsersResponse
import com.github.htdangkhoa.cleanarchitecture.domain.auth.AuthParam
import com.github.htdangkhoa.cleanarchitecture.domain.auth.AuthUseCase
import com.github.htdangkhoa.cleanarchitecture.domain.user.UserParam
import com.github.htdangkhoa.cleanarchitecture.domain.user.UserUseCase
import com.github.htdangkhoa.cleanarchitecture.extension.liveDataOf
import com.github.htdangkhoa.cleanarchitecture.resource.Resource

class MainViewModel(
    private val userUseCase: UserUseCase,
    private val authUseCase: AuthUseCase
) : ViewModel() {
    val resourceUser = liveDataOf<Resource<GetMeResponse.User>>()
    val resourceUsers = liveDataOf<Resource<Array<UsersResponse.User>>>()
    val resourceLogout = liveDataOf<Resource<String>>()

    fun getMe() {
        resourceUser.postValue(Resource.loading())

        userUseCase.execute<GetMeResponse.User> (UserParam(UserParam.Type.GET_ME))  {
            onComplete {
                resourceUser.postValue(Resource.success(it))
            }

            onError {
                resourceUser.postValue(Resource.error(it))
            }

            onCancel {
                resourceUser.postValue(Resource.error(it))
            }
        }
    }

    fun getUsers() {
        resourceUsers.postValue(Resource.loading())

        userUseCase.execute<Array<UsersResponse.User>> (UserParam(UserParam.Type.GET_USERS)) {
            onComplete {
                resourceUsers.postValue(Resource.success(it))
            }

            onError {
                resourceUsers.postValue(Resource.error(it))
                throw it

            }

            onCancel {
                resourceUsers.postValue(Resource.error(it))
            }
        }
    }

    fun logout() {
        resourceLogout.postValue(Resource.loading())

        authUseCase.execute<String>(AuthParam(AuthParam.Type.LOGOUT)) {
            onComplete {
                resourceLogout.postValue(Resource.success(it))
            }

            onError {
                resourceLogout.postValue(Resource.error(it))
            }

            onCancel {
                resourceLogout.postValue(Resource.error(it))
            }
        }
    }
}
