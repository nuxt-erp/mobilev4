package com.github.htdangkhoa.nexterp.ui.main.fragments.home

import androidx.lifecycle.ViewModel
import com.github.htdangkhoa.nexterp.domain.auth.AuthParam
import com.github.htdangkhoa.nexterp.domain.auth.AuthUseCase
import com.github.htdangkhoa.nexterp.extension.liveDataOf
import com.github.htdangkhoa.nexterp.resource.Resource

class HomeViewModel(
    private val authUseCase: AuthUseCase

) : ViewModel() {
    val resourceLogout = liveDataOf<Resource<Array<String?>>>()


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
