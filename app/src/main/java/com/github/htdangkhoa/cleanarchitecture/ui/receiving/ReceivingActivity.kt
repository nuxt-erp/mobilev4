package com.github.htdangkhoa.cleanarchitecture.ui.receiving

import android.os.Bundle
import com.github.htdangkhoa.cleanarchitecture.R
import com.github.htdangkhoa.cleanarchitecture.base.BaseActivity
import com.github.htdangkhoa.cleanarchitecture.resource.ObserverResource


class ReceivingActivity : BaseActivity<ReceivingViewModel>(ReceivingViewModel::class) {
    override val layoutResID: Int
        get() = R.layout.activity_receiving

    override fun render(savedInstanceState: Bundle?) {
        viewModel.resourceLogout.observe(this, object : ObserverResource<String>() {
            override fun onSuccess(data: String) {
                logout(401)
            }

            override fun onError(throwable: Throwable?) {
                handleError(throwable) {
                    it?.message?.let {
                        showDialog("Error", it)
                    }
                }
            }
        })
    }
}
