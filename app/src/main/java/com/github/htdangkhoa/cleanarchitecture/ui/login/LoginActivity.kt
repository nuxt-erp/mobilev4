package com.github.htdangkhoa.cleanarchitecture.ui.login

import android.os.Bundle
import com.github.htdangkhoa.cleanarchitecture.R
import com.github.htdangkhoa.cleanarchitecture.base.BaseActivity
import com.github.htdangkhoa.cleanarchitecture.data.model.AuthModel
import com.github.htdangkhoa.cleanarchitecture.data.remote.auth.AuthResponse
import com.github.htdangkhoa.cleanarchitecture.resource.ObserverResource
import com.github.htdangkhoa.cleanarchitecture.ui.settings.SettingsActivity
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import com.pawegio.kandroid.startActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<LoginViewModel>(LoginViewModel::class) {
    override val layoutResID: Int
        get() = R.layout.activity_login

    override fun render(savedInstanceState: Bundle?) {
        viewModel.resourceToken.observe(this, object : ObserverResource<AuthResponse.Token>() {
            override fun onSuccess(data: AuthResponse.Token) {
                data.apply {
                    AuthModel.accessToken = access_token
                    AuthModel.refreshToken = refresh_token
                }

                startActivity<SettingsActivity>()

                finishAfterTransition()
            }

            override fun onError(throwable: Throwable?) {
                handleError(throwable) {
                    it?.message?.let {
                        showDialog(message = it).apply {
                            positiveButton {
                                handleHttpError(throwable)
                            }
                        }
                    }
                }
            }

            override fun onLoading(isShow: Boolean) {
                progressCircular.apply {
                    if (isShow) show() else hide(true)
                }
            }
        })

        btnLogin.setOnClickListener {
            viewModel.login(
                edtUsername.text.toString(), edtPassword.text.toString()
            )
        }
    }
}
