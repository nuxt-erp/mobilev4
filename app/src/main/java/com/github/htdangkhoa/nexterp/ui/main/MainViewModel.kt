package com.github.htdangkhoa.nexterp.ui.main

import androidx.lifecycle.ViewModel
import com.github.htdangkhoa.nexterp.data.remote.location.LocationResponse
import com.github.htdangkhoa.nexterp.data.remote.user.GetMeResponse
import com.github.htdangkhoa.nexterp.data.remote.user.UsersResponse
import com.github.htdangkhoa.nexterp.domain.auth.AuthParam
import com.github.htdangkhoa.nexterp.domain.auth.AuthUseCase
import com.github.htdangkhoa.nexterp.domain.location.LocationParam
import com.github.htdangkhoa.nexterp.domain.location.LocationUseCase
import com.github.htdangkhoa.nexterp.domain.user.UserParam
import com.github.htdangkhoa.nexterp.domain.user.UserUseCase
import com.github.htdangkhoa.nexterp.extension.liveDataOf
import com.github.htdangkhoa.nexterp.resource.Resource

class MainViewModel(
    private val userUseCase: UserUseCase,
    private val locationUseCase: LocationUseCase,
    private val authUseCase: AuthUseCase
) : ViewModel() {
    val resourceUser = liveDataOf<Resource<GetMeResponse.User>>()
    val resourceUsers = liveDataOf<Resource<Array<UsersResponse.User>>>()
    val resourceLocations = liveDataOf<Resource<Array<LocationResponse.Location>>>()
    val resourceLogout = liveDataOf<Resource<Array<String?>>>()

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
    fun getLocations() {
        resourceLocations.postValue(Resource.loading())

        locationUseCase.execute<Array<LocationResponse.Location>> (LocationParam(LocationParam.Type.GET_LOCATIONS)) {
            onComplete {
                resourceLocations.postValue(Resource.success(it))
            }

            onError {
                resourceLocations.postValue(Resource.error(it))
                throw it

            }

            onCancel {
                resourceLocations.postValue(Resource.error(it))
            }
        }
    }
    fun logout() {
        resourceLogout.postValue(Resource.loading())

        authUseCase.execute<Array<String?>>(AuthParam(AuthParam.Type.LOGOUT)) {
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
