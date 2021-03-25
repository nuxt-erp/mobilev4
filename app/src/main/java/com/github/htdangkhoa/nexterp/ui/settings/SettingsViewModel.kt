package com.github.htdangkhoa.nexterp.ui.settings

import androidx.lifecycle.ViewModel
import com.github.htdangkhoa.nexterp.data.remote.config.ConfigResponse
import com.github.htdangkhoa.nexterp.data.remote.location.LocationResponse
import com.github.htdangkhoa.nexterp.data.remote.user.GetMeResponse
import com.github.htdangkhoa.nexterp.data.remote.user.UsersResponse
import com.github.htdangkhoa.nexterp.domain.auth.AuthParam
import com.github.htdangkhoa.nexterp.domain.auth.AuthUseCase
import com.github.htdangkhoa.nexterp.domain.config.ConfigParam
import com.github.htdangkhoa.nexterp.domain.config.ConfigUseCase
import com.github.htdangkhoa.nexterp.domain.location.LocationParam
import com.github.htdangkhoa.nexterp.domain.location.LocationUseCase
import com.github.htdangkhoa.nexterp.domain.user.UserParam
import com.github.htdangkhoa.nexterp.domain.user.UserUseCase
import com.github.htdangkhoa.nexterp.extension.liveDataOf
import com.github.htdangkhoa.nexterp.resource.Resource

class SettingsViewModel(
    private val userUseCase: UserUseCase,
    private val locationUseCase: LocationUseCase,
    private val configUseCase: ConfigUseCase
) : ViewModel() {
    val resourceUser = liveDataOf<Resource<GetMeResponse.User>>()
    val resourceUsers = liveDataOf<Resource<Array<UsersResponse.User>>>()
    val resourceLocations = liveDataOf<Resource<Array<LocationResponse.Location>>>()
    val resourceConfig = liveDataOf<Resource<Array<ConfigResponse.Config>>>()

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
    fun getConfig() {
        resourceConfig.postValue(Resource.loading())

        configUseCase.execute<Array<ConfigResponse.Config>> (ConfigParam(ConfigParam.Type.GET_CONFIG)) {
            onComplete {
                resourceConfig.postValue(Resource.success(it))
            }

            onError {
                resourceConfig.postValue(Resource.error(it))
                throw it
            }

            onCancel {
                resourceConfig.postValue(Resource.error(it))
            }
        }
    }
}
